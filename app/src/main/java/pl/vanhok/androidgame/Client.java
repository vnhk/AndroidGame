package pl.vanhok.androidgame;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements ServerClient{
    private boolean on = false;
    private Socket s;
    private PrintWriter pw;
    private int PORT;
    private String host;
    private final String logString = "CLIENT";

    public Client(int PORT, String host){
        this.PORT = PORT;
        this.host = host;
    }

    public boolean connect() {
        try{
            s = new Socket(host,PORT);
            on = true;
        }catch (IOException e){
            Log.i(logString, "connect: "+e.getMessage());
            //close();
            return false;
        }
        return true;
    }

    public boolean isOn() {
        return on;
    }

    @Override
    public void close() {
        try {
            if(s!=null)
                s.close();
            Log.i(logString, "sockets - closed");
        }catch (IOException e){
            Log.i(logString, "connect: "+e.getMessage());
        }
    }
}
