package pl.vanhok.stickwarapp;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Enumeration;

public final class Server extends ServerClient {

    private static Server instance = null;
    private boolean on = false;
    private ServerSocket ss;

    public static Server getInstance(){
        if(instance == null){
            instance = new Server();
        }
        return instance;
    }

    private Server(){
        logString = "Server";
        try {
            ss = new ServerSocket(PORT);
            on = true;
            Log.i(logString, "Server: IP: "+getIpAddress());
        }catch (IOException e) {
            Log.i(logString, "Constructor: " + e.getMessage());
            close();
        }
    }

    public void accept() {
        try {
            socket = ss.accept();
            Log.i(logString, "Client connected");
        }catch (IOException e){
            Log.i(logString, "accept: "+ e.getMessage());
        }
    }

    public String getIpAddress() {
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
    void close() {
        try {
            if(ss!=null)
                ss.close();
            if(socket!=null)
                socket.close();
            Log.i(logString, "sockets - closed");
        }catch (IOException e){
            Log.i(logString, "Constructor: " + e.getMessage());
        }
    }
}
