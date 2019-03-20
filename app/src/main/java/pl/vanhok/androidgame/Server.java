package pl.vanhok.androidgame;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class Server implements ServerClient{

    private final String logString = "SERVER";
    private boolean on = false;
    private ServerSocket ss;
    private Socket s;
    public Server(int PORT) {
        try {
            ss = new ServerSocket(PORT);
            on = true;
            Log.i(logString, "Server: IP: "+getIpAddress());
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

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "Server running at : "
                                + inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
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
