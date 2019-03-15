package pl.vanhok.androidgame;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ServerClient{

    private final String logString = "SERVER";
    private boolean on = false;
    private ServerSocket ss;
    private Socket s;
    public Server(int PORT) {
        try {
            ss = new ServerSocket(PORT);
            on = true;
        }catch (IOException e) {
            Log.i(logString, "Constructor: " + e.getMessage());
            close();
        }
    }

    public boolean isOn() {
        return on;
    }


    public void accept() {
        try {
            s = ss.accept();
            Log.i(logString, "Client connected");
        }catch (IOException e){
            Log.i(logString, "accept: "+ e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            if(ss!=null)
                ss.close();
            if(s!=null)
                s.close();
            Log.i(logString, "sockets - closed");
        }catch (IOException e){
            Log.i(logString, "Constructor: " + e.getMessage());
        }
    }
}
