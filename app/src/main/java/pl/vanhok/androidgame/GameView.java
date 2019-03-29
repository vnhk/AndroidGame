package pl.vanhok.androidgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView {
    private SurfaceHolder holder;

    public GameView(Context ct){
        super(ct);
        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("WrongCall")
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas();
                onDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    protected void onDraw(Canvas c){
        super.onDraw(c);
        c.drawCircle(10,10,20,new Paint(Color.RED));
    }
}