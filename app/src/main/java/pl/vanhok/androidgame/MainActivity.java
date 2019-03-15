package pl.vanhok.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.net.ServerSocket;

public class MainActivity extends Activity {
    private Server server;
    private Client client;
    private final int PORT = 4990;
    private final String HOST = "localhost";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameView(this));

        serverClientConnection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(client!=null&&client.isOn())
            client.close();
        if(server!=null&&server.isOn())
            server.close();

    }

    void serverClientConnection() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               client = new Client(PORT,HOST);
                if(!client.isOn()) {
                    server = new Server(PORT);
                }
                if(client.isOn())
                    clientOperation();
                if(server!=null&&server.isOn())
                    serverOperation();
            }
        });
        t.start();
    }

    void clientOperation(){
        Log.i("CLIENT", "clientOperation: client start");
    }
    void serverOperation(){
        Log.i("SERVER", "serverOperation: server start");
        server.accept();
    }
}
