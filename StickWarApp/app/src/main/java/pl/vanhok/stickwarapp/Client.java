package pl.vanhok.stickwarapp;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public final class Client extends ServerClient {

    private static Client instance = null;
    public static String host = "localhost";

    public static Client getInstance(){
        if(instance == null){
            instance = new Client();
        }
        return instance;
    }

    private Client(){
        logString = "CLIENT";
    }

    public boolean connect() {
        try{
            socket = new Socket(host,PORT);
            on = true;
        }catch (IOException e){
            Log.i(logString, "connect: "+e.getMessage());
            //close();
            return false;
        }
        return true;
    }


    @Override
    public void close() {
        try {
            if(socket!=null)
                socket.close();
            Log.i(logString, "sockets - closed");
        }catch (IOException e){
            Log.i(logString, "connect: "+e.getMessage());
        }
    }
}
