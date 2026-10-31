package javax.microedition.rms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Desktop shim for the MIDP {@link RecordStore}.
 *
 * Each store is persisted as a single file below
 * {@code ~/.the_sims_3_mobile/rms}. The game only uses the store as a flat list
 * of byte records (settings and save games), so a simple whole-store rewrite is
 * sufficient.
 */
public class RecordStore {
    private static final String STORE_SUFFIX = ".rms";

    private final String name;
    private final File file;
    private final TreeMap<Integer, byte[]> records = new TreeMap<>();
    private int nextRecordId = 1;
    private long lastModified;
    private boolean open = true;

    private RecordStore(String name, File file) {
        this.name = name;
        this.file = file;
        this.lastModified = System.currentTimeMillis();
    }

    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary)
            throws RecordStoreException {
        File dir = storageDirectory();
        File file = new File(dir, sanitize(recordStoreName) + STORE_SUFFIX);
        if (!file.exists() && !createIfNecessary) {
            throw new RecordStoreNotFoundException("no record store named " + recordStoreName);
        }
        RecordStore store = new RecordStore(recordStoreName, file);
        if (file.exists()) {
            store.load();
        } else {
            store.save();
        }
        return store;
    }

    public static void deleteRecordStore(String recordStoreName) throws RecordStoreException {
        File file = new File(storageDirectory(), sanitize(recordStoreName) + STORE_SUFFIX);
        if (!file.exists()) {
            throw new RecordStoreNotFoundException("no record store named " + recordStoreName);
        }
        if (!file.delete()) {
            throw new RecordStoreException("could not delete store " + recordStoreName);
        }
    }

    public static String[] listRecordStores() {
        File[] files = storageDirectory().listFiles();
        if (files == null) {
            return null;
        }
        List<String> names = new ArrayList<>();
        for (File storeFile : files) {
            String storeName = storeFile.getName();
            if (storeName.endsWith(STORE_SUFFIX)) {
                names.add(storeName.substring(0, storeName.length() - STORE_SUFFIX.length()));
            }
        }
        return names.toArray(new String[0]);
    }

    public String getName() throws RecordStoreNotOpenException {
        checkOpen();
        return name;
    }

    public int addRecord(byte[] data, int offset, int numBytes)
            throws RecordStoreException {
        checkOpen();
        int id = nextRecordId++;
        records.put(id, copy(data, offset, numBytes));
        lastModified = System.currentTimeMillis();
        save();
        return id;
    }

    public void setRecord(int recordId, byte[] newData, int offset, int numBytes)
            throws RecordStoreException {
        checkOpen();
        if (!records.containsKey(recordId)) {
            throw new InvalidRecordIDException("invalid record id " + recordId);
        }
        records.put(recordId, copy(newData, offset, numBytes));
        lastModified = System.currentTimeMillis();
        save();
    }

    public byte[] getRecord(int recordId) throws RecordStoreException {
        checkOpen();
        byte[] data = records.get(recordId);
        if (data == null) {
            throw new InvalidRecordIDException("invalid record id " + recordId);
        }
        return data.clone();
    }

    public int getRecordSize(int recordId) throws RecordStoreException {
        checkOpen();
        byte[] data = records.get(recordId);
        if (data == null) {
            throw new InvalidRecordIDException("invalid record id " + recordId);
        }
        return data.length;
    }

    public void deleteRecord(int recordId) throws RecordStoreException {
        checkOpen();
        if (records.remove(recordId) == null) {
            throw new InvalidRecordIDException("invalid record id " + recordId);
        }
        lastModified = System.currentTimeMillis();
        save();
    }

    public int getNumRecords() throws RecordStoreNotOpenException {
        checkOpen();
        return records.size();
    }

    public int getNextRecordID() throws RecordStoreNotOpenException {
        checkOpen();
        return nextRecordId;
    }

    public int getSize() throws RecordStoreNotOpenException {
        checkOpen();
        int size = 0;
        for (Map.Entry<Integer, byte[]> entry : records.entrySet()) {
            size += entry.getValue().length + 12;
        }
        return size;
    }

    public int getSizeAvailable() throws RecordStoreNotOpenException {
        checkOpen();
        return 65536;
    }

    public long getLastModified() throws RecordStoreNotOpenException {
        checkOpen();
        return lastModified;
    }

    public void closeRecordStore() throws RecordStoreNotOpenException {
        checkOpen();
        open = false;
        try {
            save();
        } catch (RecordStoreException failure) {
            System.err.println("warning: could not persist record store " + name + ": " + failure.getMessage());
        }
    }

    public void addRecordListener(RecordListener listener) {
    }

    public void removeRecordListener(RecordListener listener) {
    }

    private void checkOpen() throws RecordStoreNotOpenException {
        if (!open) {
            throw new RecordStoreNotOpenException("store " + name + " is closed");
        }
    }

    private void load() throws RecordStoreException {
        try (DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
            nextRecordId = stream.readInt();
            int count = stream.readInt();
            for (int index = 0; index < count; index++) {
                int id = stream.readInt();
                int length = stream.readInt();
                byte[] data = new byte[length];
                stream.readFully(data);
                records.put(id, data);
            }
        } catch (Exception failure) {
            throw new RecordStoreException("could not read store " + name + ": " + failure);
        }
    }

    private void save() throws RecordStoreException {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new RecordStoreException("could not create " + parent);
        }
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
            out.writeInt(nextRecordId);
            out.writeInt(records.size());
            for (Map.Entry<Integer, byte[]> entry : records.entrySet()) {
                out.writeInt(entry.getKey());
                out.writeInt(entry.getValue().length);
                out.write(entry.getValue());
            }
        } catch (Exception failure) {
            throw new RecordStoreException("could not write store " + name + ": " + failure);
        }
    }

    private static byte[] copy(byte[] data, int offset, int numBytes) {
        byte[] result = new byte[numBytes];
        if (data != null && numBytes > 0) {
            System.arraycopy(data, offset, result, 0, numBytes);
        }
        return result;
    }

    private static File storageDirectory() {
        String base = System.getProperty("midp.home", System.getProperty("user.home") + "/.the_sims_3_mobile");
        File dir = new File(base, "rms");
        if (!dir.exists() && !dir.mkdirs()) {
            System.err.println("warning: could not create record store directory " + dir);
        }
        return dir;
    }

    private static String sanitize(String name) {
        return name.replaceAll("[^A-Za-z0-9._-]", "_");
    }
}
