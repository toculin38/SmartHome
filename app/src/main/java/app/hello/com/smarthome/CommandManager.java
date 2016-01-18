package app.hello.com.smarthome;

import android.os.HandlerThread;
import android.os.Handler;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by 邱偉 on 2016/1/17.
 */
public class CommandManager
{
    private Handler cm;
    private HandlerThread cmThread;
    public CommandManager(){
        cmThread = new HandlerThread("name");
        cmThread.start();
        cm = new Handler(cmThread.getLooper());
    }
    public boolean connectToServer(String address,int port){
        Connect connect = new Connect(address,port);
        cm.post(connect);
        synchronized(connect){
            return connect.result;
        }
    }

    private class Connect implements Runnable{
        String address;
        int port;
        boolean result;
        public Connect(String address,int port){
            this.address = address;
            this.port = port;
        }
        public void run() {
            synchronized(this){
                Socket client = new Socket();
                InetSocketAddress isa = new InetSocketAddress(address, port);
                try {
                    client.connect(isa, 3000);
                    result = true;
                } catch (java.io.IOException e) {
                    System.out.println("IOException :" + e.toString());
                    result = false;
                }
            }
        }
    }

}
