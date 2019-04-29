package pl.vanhok.stickwarapp.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class GameView extends SurfaceView {

    private SurfaceHolder holder;
    private GameThread gameThread;
    public static int x = 100, y = 100;

    private GameSimulation game;

    public void update() {
    }


    @Override
    public boolean performClick() {
        super.performClick();
        Log.i("Game", "performClick: ");
        return true;
    }

    public GameView(Context ct, String x, int screenWidth, int screenHeight) {
        super(ct);
        game = new GameSimulation(x, screenWidth, screenHeight);
        this.setFocusable(true);
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getY() > getHeight() / 2) {
                        if (event.getX() < getWidth() / 2) {
                            game.setDirection("left");
                        } else {
                            game.setDirection("right");
                        }
                    } else {
                        game.setBullet("true");
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


        holder = getHolder();
        this.setFocusable(true);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameThread = new GameThread(GameView.this, holder);
                gameThread.setRunning(true);
                gameThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                while (retry) {
                    try {
                        game.setRunning(false);
                        game.getOperationThread().join();
                        gameThread.setRunning(false);
                        gameThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    retry = true;
                }
            }
        });
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        game.control();
        game.draw(c);
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        invalidate();
    }
}