package pl.vanhok.stickwarapp.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import pl.vanhok.stickwarapp.ServerClient.Client;
import pl.vanhok.stickwarapp.ServerClient.Server;


public final class GameSimulation {
    private PlayerOne playerOne;
    private PlayerTwo playerTwo;
    private String direction = "left", directionEnemy = "left";
    private final float surfaceWidth, surfaceHeight;
    private Server server;
    private Client client;
    private String message;
    private final int sleepTime = 100;
    private Thread operationThread;
    private final double speedRatio = 0.02;
    private final double ratio = 0.1;
    private boolean running = true;
    private String bullet = "null";
    private String enemyPoints, enemyPosition, enemyBullet = "null";

    public String getBullet() {
        return bullet;
    }

    public void setBullet(String bullet) {
        this.bullet = bullet;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


    public void setRunning(boolean running) {
        this.running = running;
    }

    private void receiveMessage() {
        if (server != null) {
            message = server.receive();
        } else if (client != null) {
            message = client.receive();
        }
        Log.i("communication", "run: " + message);
        String[] messages = message.split(",");
        try {
            enemyPoints = messages[ReceivedMessage.POINTS.ordinal()];
            enemyPosition = messages[ReceivedMessage.POSITION.ordinal()];
            enemyBullet = messages[ReceivedMessage.BULLET.ordinal()];
        } catch (IndexOutOfBoundsException iuoe) {
        }
    }

    private void changePosition() {

        if (message.equals("") && running) {
            setRunning(false);
            return;
        }

        if (!enemyPosition.equals(""))
            directionEnemy = enemyPosition;
    }

    private void changePoints() {
        if (!enemyPoints.equals(""))
            playerTwo.setPoints(Integer.parseInt(enemyPoints));
    }


    private void sendMessage() {
        String str = "";
        str += direction + "," + playerOne.getPoints() + "," + bullet;
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

        playerOne.setPosx(ratio * width);
        playerOne.setPosy((1 - ratio) * height);
        playerOne.setSize(ratio * width);
        playerOne.setSpeed(speedRatio * width);

        playerTwo.setPosx(ratio * width);
        playerTwo.setPosy((ratio) * height);
        playerTwo.setSize(ratio * width);
        playerTwo.setSpeed(speedRatio * width);


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
                    changePoints();
                }
            }
        });
        operationThread.start();
    }

    private void colisionOperation(Bullet bullet) {
        if (bullet.getPosx() + bullet.getSize() / 2 >= playerTwo.getPosx()
                && bullet.getPosx() + bullet.getSize() / 2 <= playerTwo.getPosx() + playerTwo.getSize() / 2
        )
            playerOne.setPoints(playerOne.getPoints() + 1);
    }

    private void playerPositionControl(Player player, String directionL) {
        double posx = player.getPosx();
        double speed = player.getSpeed();

        if (directionL.equals("left"))
            if (speed > 0)
                speed = -speed;

        if (directionL.equals("right"))
            if (speed < 0)
                speed = -speed;

        player.setSpeed(speed);

        posx += speed;

        if (posx <= player.getSize()) {
            posx = player.getSize();
        }
        if (posx >= surfaceWidth - (player.getSize())) {
            posx = surfaceWidth - (player.getSize());
        }

        player.setPosx(posx);
    }


    void playerBulletControl(Player player, String bulletDesc) {

        if (bulletDesc.equals("true")) {
            if (player.getBullet() == null) {
                player.initBullet();
            }
        }

        if (player.getBullet() != null) {
            player.getBullet().update();
            if (bulletDesc.equals("null")) {
                player.setBullet(null);
            }
        }

    }

    void control() {
        if (playerOne.getBullet() != null)
            if (playerOne.getBullet().getPosy() < 50) {
                colisionOperation(playerOne.getBullet());
                playerOne.setBullet(null);
                bullet = "null";
            }
        playerPositionControl(playerOne, direction);
        playerPositionControl(playerTwo, directionEnemy);
        playerBulletControl(playerTwo, enemyBullet);
        playerBulletControl(playerOne, bullet);

    }


    void draw(Canvas c) {
        int textColor = Color.rgb(0, 0, 0);

        c.drawColor(Color.rgb(68, 95, 195));


        if (playerOne.getBullet() != null) {
            playerOne.getBullet().draw(c);
        }
        if (playerTwo.getBullet() != null) {
            playerTwo.getBullet().draw(c);
        }

        c.drawText(String.valueOf(playerOne.getPoints()), surfaceWidth / 10, (float) (surfaceHeight / 1.8), new Paint(textColor));
        c.drawText(String.valueOf(playerTwo.getPoints()), surfaceWidth / 10, (float) (surfaceHeight / 2.2), new Paint(textColor));
        c.drawCircle((float) playerOne.getPosx(),
                (float) playerOne.getPosy(), (float) playerOne.getSize(), playerOne.getColor());

        c.drawCircle((float) playerTwo.getPosx(),
                (float) playerTwo.getPosy(), (float) playerTwo.getSize()
                , playerTwo.getColor());




    }
}
