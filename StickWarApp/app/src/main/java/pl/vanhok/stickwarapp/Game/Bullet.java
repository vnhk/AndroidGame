package pl.vanhok.stickwarapp.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet {
    private final double speed;
    private final double size;
    private double posx;
    private double posy;

    public Bullet(double posx,double posy,double speed,double size){
        if(speed>0)
            speed = -speed;
        this.speed = 2*speed;
        this.posx = posx;
        this.posy = posy;
        this.size = size/2;
    }
    public void draw(Canvas c){
        c.drawCircle((float) posx,(float)posy,(float)(size),new Paint(Color.GRAY));
    }

    public void update(){
        posy+=speed;
    }


    public double getSpeed() {
        return speed;
    }

    public double getSize() {
        return size;
    }

    public double getPosx() {
        return posx;
    }

    public void setPosx(double posx) {
        this.posx = posx;
    }

    public double getPosy() {
        return posy;
    }

    public void setPosy(double posy) {
        this.posy = posy;
    }
}
