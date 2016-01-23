package app.hello.com.smarthome;

import android.os.HandlerThread;
import android.os.Handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by 邱偉 on 2016/1/17.
 */
public class CommandManager
{
    public final static int port = 8888;
    private Socket client;
    private Handler handler;
    private HandlerThread handlerThread;

    public CommandManager(){
        client = new Socket();
        handlerThread = new HandlerThread("name");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    public synchronized void connectToServer(String address){
        Connect connect = new Connect(address,port);
        handler.post(connect);
        while (!connect.isDone()){
            try{
                wait(30);
            }
            catch (InterruptedException e){
                System.out.println(e.toString());
            }
        }
    }

    public boolean isConnecting(){
        PrintStream out;
        try {
             out = new PrintStream(client.getOutputStream());
        } catch (IOException e){
            return false;
        }
        return !(out.checkError());
    }

    public synchronized void disconnect(){
        try {
            client.close();
            client = new Socket();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public void sendCommand(String command){
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(command);
            out.flush();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    private class Connect implements Runnable{
        private String address;
        private int port;
        public boolean done;
        public Connect(String address,int port){
            this.address = address;
            this.port = port;
            this.done = false;
        }
        public boolean isDone(){
            return done;
        }
        public void run() {
            synchronized(this){
                try {
                    InetAddress serverAddr = InetAddress.getByName(address);
                    SocketAddress sc_add = new InetSocketAddress(serverAddr, port);
                    client.connect(sc_add,3000);
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
                done = true;
            }
        }
    }
}
