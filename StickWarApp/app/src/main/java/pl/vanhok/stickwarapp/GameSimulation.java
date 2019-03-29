package pl.vanhok.stickwarapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.RectShape;

public final class GameSimulation {
    //2 tablice na 4 przecuwbujiw dla kazdego/
    //wiadomosci kiedy atak
    //ilosc zlota
    PlayerOne playerOne;
    PlayerTwo playerTwo;

    public void setDirection(String direction) {
        this.direction = direction;
    }

    String direction;
    int surfaceWidth,surfaceHeight;

    abstract class Player{
        protected final int width,height;
        protected Paint color;
        protected int posx,posy;
        protected int speed = 10;
        Player(){
            width=height=200;
            color = new Paint();
            color.setStyle(Paint.Style.STROKE);
            color.setStrokeWidth(10);
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public Paint getColor() {
            return color;
        }

        public int getPosx() {
            return posx;
        }

        public int getPosy() {
            return posy;
        }

        public void setPosx(int posx) {
            this.posx = posx;
        }

        public void setPosy(int posy) {
            this.posy = posy;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }
    }
    class PlayerOne extends Player{
        PlayerOne(){
            posx = posy = 50;
            color.setColor(Color.BLACK);
        }
    }

    class PlayerTwo extends Player{
        PlayerTwo(){
            posx = getWidth()-50;
            posy = 50;
            color.setColor(Color.RED);
        }
    }


    GameSimulation(int width,int height){
        surfaceWidth = width;
        surfaceHeight = height;
        playerOne = new PlayerOne();
        playerTwo = new PlayerTwo();
    }

    void control(){
        int posx = playerOne.getPosx();
        int posy = playerOne.getPosy();
        int height = playerOne.getHeight();
        int width = playerOne.getWidth();
        int speed = playerOne.getSpeed();

        if(direction.equals("up"))
            if(speed>0)
                speed=speed*(-1);

        if(direction.equals("down"))
            if(speed<0)
                speed=speed*(-1);
         playerOne.setSpeed(speed);

         posy+=speed;
         posy++;

        if(posy<=0){
            playerOne.setPosy(0);
        }
        if(posx<=0){
            playerOne.setPosx(0);
        }
        if(posy-height>=surfaceHeight){
            posy=surfaceHeight-height;
        }
        if(posx-width>=surfaceWidth/2){
            posx=surfaceWidth/2-width;
        }
        playerOne.setPosy(posy);


    }


    void draw(Canvas c) {
        c.drawRect(playerOne.getPosx(),
                playerOne.getPosy(), playerOne.getWidth()
                , playerOne.getHeight(), playerOne.getColor());

        c.drawRect(playerTwo.getPosx(),
                playerTwo.getPosy(), playerTwo.getWidth()
                , playerTwo.getHeight(), playerTwo.getColor());
    }
}
