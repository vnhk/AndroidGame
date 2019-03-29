package pl.vanhok.stickwarapp;

import android.graphics.Bitmap;

public abstract class GameNpc {
   protected int dmg;
   protected int hp;
   protected int attackTimeMs;
   protected int cost;
   private long last_attack = System.currentTimeMillis();

   public int attack(){
       long now = System.currentTimeMillis();
       if(now-last_attack<attackTimeMs){
           return 0;
       }
       return dmg;
   }

   public void defence(int dmg){
       if(hp-dmg<=0){
           //dead
           return;
       }
       hp-=dmg;
   }

}

