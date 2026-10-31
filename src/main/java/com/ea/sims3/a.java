package com.ea.sims3;

import javax.microedition.lcdui.Image;

public final class a extends w implements h {
   private static final String[] a14 = new String[]{"TETPOP", "SPORE1", "MNOPWW"};

   public a(int var1, int var2) {
      super(var1, var2);
   }

   public final Image a3(String var1) {
      for (int var2 = a14.length - 1; var2 != -1; var2--) {
         if (var1.equals(a14[var2])) {
            int var3 = 0;
            if (var2 == 2) {
               var3 = com.ea.sims3.r.a7().i2;
            }

            return t.a3(195, var2, var3);
         }
      }

      return null;
   }
}
