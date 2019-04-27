package pl.vanhok.stickwarapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ServerClient {
    private String message;
    private String response;
    protected String logString;
    protected Socket socket;
    protected boolean on = false;
    protected final int PORT = 10080;
    PrintWriter pw;
    BufferedReader br;
    InputStreamReader in;

    public boolean isOn() {
        return on;
    }

    public void prepareCommunication() {
        try {
            in = new InputStreamReader(socket.getInputStream());
            pw = new PrintWriter(socket.getOutputStream());
            br = new BufferedReader(in);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    public String receive(){
            try{
                response = br.readLine();
                if(response != null)
                    return response;
            }catch (Exception e){
                Log.i(logString, "communication: "+e.getMessage());
            }
        return "";
    }

    public void send(String message){
            try{
                pw.println(message);
                pw.flush();
            }catch (Exception e) {
                Log.i(logString, "communication: " + e.getMessage());
            }
    }

    abstract void close();


}
