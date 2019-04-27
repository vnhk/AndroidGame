package pl.vanhok.stickwarapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;

abstract class Player {
    protected Paint color;
    protected double posx, posy;
    protected double speed;
    protected double size;

    Player() {
        color = new Paint();
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

public final class GameSimulation {
    private PlayerOne playerOne;
    private PlayerTwo playerTwo;
    private String direction;
    private final int surfaceWidth, surfaceHeight;
    private Server server;
    private Client client;
    private String message;
    private final int sleepTime = 20;
    private Thread operationThread;
    private final double speedRatio = 0.01;
    private final double ratio = 0.1;
    private boolean running = true;


    public void setDirection(String direction) {
        this.direction = direction;
    }


    public void setRunning(boolean running){
        this.running = running;
    }
    private void receiveMessage() {
        if (server != null) {
            message = server.receive();
        } else if (client != null) {
            message = client.receive();
        }
        Log.i("communication", "run: " + message);
    }

    private void changePosition() {

        if (message != null&&!message.equals(""))
            playerTwo.setPosx(Float.parseFloat(message)*surfaceWidth);
    }


    private void sendMessage() {
       String str = "";
       str += playerOne.getPosx()/surfaceWidth;
        if (server != null) {
            server.send(str);
        } else if (client != null) {
            client.send(str);
        }

    }

    public Thread getOperationThread() {
        return operationThread;
    }

    GameSimulation(String x, int width, int height) {
        surfaceWidth = width;
        surfaceHeight = height;

        playerOne = new PlayerOne();
        playerTwo = new PlayerTwo();

        playerOne.setPosx(ratio*width);
        playerOne.setPosy(ratio*height);
        playerOne.setSize(ratio*width);
        playerOne.setSpeed(speedRatio*width);

        playerTwo.setPosx(ratio*width);
        playerTwo.setPosy((1-ratio)*height);
        playerTwo.setSize(ratio*width);
        playerTwo.setSpeed(speedRatio*width);



        if (x.equals("SERVER")) {
            server = Server.getInstance();
            client = null;
            server.prepareCommunication();

        } else {
            client = Client.getInstance();
            server = null;
            client.prepareCommunication();

        }


        operationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sendMessage();
                    receiveMessage();
                    changePosition();
                }
            }
        });
    operationThread.start();
    }


    void control() {
        double posx = playerOne.getPosx();
        double speed = playerOne.getSpeed();

        if (direction.equals("left"))
            if (speed > 0)
                speed = -speed;

        if (direction.equals("right"))
            if (speed < 0)
                speed = -speed;

        playerOne.setSpeed(speed);

        posx += speed;

        if (posx <= playerOne.getSize() / 2.1) {
            posx = playerOne.getSize() / 2.1;
        }
        if (posx >= surfaceWidth - (playerOne.getSize() / 2.1)){
            posx = surfaceWidth - (playerOne.getSize() / 2.1);
        }

        playerOne.setPosx(posx);
    }


    void draw(Canvas c) {
        c.drawCircle((float)playerOne.getPosx(),
                (float)playerOne.getPosy(), (float)playerOne.getSize(), playerOne.getColor());

        c.drawCircle((float)playerTwo.getPosx(),
                (float)playerTwo.getPosy(), (float)playerTwo.getSize()
                , playerTwo.getColor());
    }
}
