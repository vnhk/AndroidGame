package pl.vanhok.stickwarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Server server = null;
    private Client client = null;
    private Button button;
    private TextView tv;
    private EditText ed;
    private String HOST = "10.0.2.2";
    private RadioButton r1,r2;
    private final int SERVER = 1;
    private final int CLIENT = 0;
    int xd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        r1 = findViewById(R.id.radioButton);
        r2 = findViewById(R.id.radioButton2);
        tv = findViewById(R.id.textViewInfo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed = findViewById(R.id.editText);
                HOST = ed.getText().toString();
                if(r1.isChecked())
                    serverClientConnection(SERVER);
                else if(r2.isChecked())
                    serverClientConnection(CLIENT);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(client!=null&&client.isOn())
            client.close();
        if(server!=null&&server.isOn())
            server.close();

    }

    void serverClientConnection(final int type) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(type==0) {
                    Client.host = HOST;
                    client = Client.getInstance();
                    client.connect();
                }
                else {
                    server = Server.getInstance();
                }
                if(client!=null&&client.isOn()) {
                    clientOperation();
                    return;
                }
                if(server!=null) {
                    serverOperation();
                }
            }
        });
        t.start();
    }

    void clientOperation(){
        Log.i("CLIENT", "clientOperation: client start");
        Intent i = new Intent(getApplicationContext(),GameMainActivity.class);
        i.putExtra("SERVERCLIENT","CLIENT");
        startActivity(i);
    }
    void serverOperation(){
        Log.i("SERVER", "serverOperation: server start");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(server.getIpAddress());
            }
        });
        server.accept();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String info = "Connection accepted";
                tv.setText(info);
            }
        });
        Log.i("SERVER","accepted");

        Intent i = new Intent(getApplicationContext(),GameMainActivity.class);
        i.putExtra("SERVERCLIENT","SERVER");
        startActivity(i);

    }
}


