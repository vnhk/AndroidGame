package pl.vanhok.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {
    private Server server;
    private Client client;
    private Button button;
    private TextView tv;
    private EditText ed;
    private final int PORT = 8080;
    private String HOST = "10.0.2.2";
    private RadioButton r1,r2;
    private final int SERVER = 1;
    private final int CLIENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
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
                    Client.PORT = PORT;
                    client = Client.getInstance();
                    client.connect();
                }
                else {
                    Server.PORT = PORT;
                    server = Server.getInstance();
                }
                if(client!=null&&client.isOn())
                    clientOperation();
                if(server!=null&&server.isOn())
                    serverOperation();
            }
        });
        t.start();
    }

    void clientOperation(){
        Log.i("CLIENT", "clientOperation: client start");
        Intent i = new Intent(getApplicationContext(),GameMainActivity.class);
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
        startActivity(i);
    }
}
