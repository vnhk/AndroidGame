package pl.vanhok.stickwarapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GameMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Display display;
        display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

            String x = getIntent().getExtras().getString("SERVERCLIENT","defaultKey");
            if(!x.equals("CLIENT"))
                x = "SERVER";
            setContentView(new GameView(this,x,width,height));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
