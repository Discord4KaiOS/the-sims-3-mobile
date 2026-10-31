package com.ea.sims3;

/**
 * Desktop entry point. Creates the MIDlet and runs its lifecycle the way a
 * device's application manager would.
 */
public final class Program {
    private Program() {
    }

    public static void main(String[] args) {
        try {
            MonkeyApp midlet = new MonkeyApp();
            midlet.startApp();
        } catch (Throwable error) {
            error.printStackTrace();
            System.exit(1);
        }
    }
}
