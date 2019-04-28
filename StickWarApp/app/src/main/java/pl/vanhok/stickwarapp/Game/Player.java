package pl.vanhok.stickwarapp.Game;

import android.graphics.Color;
import android.graphics.Paint;

public abstract class Player {

    protected Paint color;
    protected double posx, posy;
    protected double speed;
    protected double size;
    protected Bullet bullet = null;
    protected int points = 0;

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    Player() {
        color = new Paint();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void initBullet() {
        bullet = new Bullet(posx, posy, speed, size);
    }


    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}


class PlayerOne extends Player {
    PlayerOne() {
        color.setColor(Color.BLACK);
    }
}

class PlayerTwo extends Player {
    PlayerTwo() {
        color.setColor(Color.RED);
    }
}


