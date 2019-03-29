package pl.vanhok.stickwarapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class GameView extends SurfaceView {
    private SurfaceHolder holder;
    private GameThread gameThread;
    public static int x =100,y = 100;
    private Server server;
    private Client client;
    private GameSimulation game = new GameSimulation(getHeight(),getWidth()); //landscape
    public void update()  {
    }

    private void receiveMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(server!=null){
                    server.receive();
                }
                else if(client!=null){
                    client.receive();
                }
            }
        }).start();
    }

    @Override
    public boolean performClick() {
        super.performClick();
        Log.i("Game", "performClick: ");
        return true;
    }

    public GameView(Context ct, String x){
        super(ct);
        this.setFocusable(true);
        this.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getX()<getWidth()/2){
                        if(event.getY()<=getHeight()/2){
                            game.setDirection("up");
                        }
                        else{
                            game.setDirection("down");
                        }
                    }
                }
                return true;
            }
        });

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setDirection("up");
            }
        });

        if(x.equals("SERVER")) {
            server = Server.getInstance();
            client = null;
            server.prepareCommunication();

        }
        else {
            client = Client.getInstance();
            server = null;
            client.prepareCommunication();

        }

        receiveMessage();

        holder = getHolder();
        this.setFocusable(true);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameThread = new GameThread(GameView.this,holder);
                gameThread.setRunning(true);
                gameThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry= true;
                while(retry) {
                    try {
                        gameThread.setRunning(false);
                        gameThread.join();
                    }catch(InterruptedException e)  {
                        e.printStackTrace();
                    }
                    retry= true;
                }
            }
        });
    }

    @Override
    public void draw(Canvas c){
        super.draw(c);
        game.control();
        c.drawColor(Color.GREEN);
        c.drawCircle(x,y,20,new Paint(Color.BLUE));
        x++;
        game.draw(c);
        try{
            Thread.sleep(30);
        }catch (Exception e){
            e.printStackTrace();
        }
        invalidate();
    }
}