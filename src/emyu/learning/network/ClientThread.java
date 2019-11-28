package emyu.learning.network;

import emyu.learning.AppConstants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientThread extends Thread {
    private DatagramSocket ds;
    private OnReceiveMessage onReceiveMessage;

    public ClientThread(OnReceiveMessage onReceiveMessage) {
        setOnReceiveMessage(onReceiveMessage);
    }

    public void setOnReceiveMessage(OnReceiveMessage onReceiveMessage) {
        this.onReceiveMessage = onReceiveMessage;
    }

    @Override
    public void run() {
        try {
            ds = new DatagramSocket();
            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, 1024);
            ds.receive(dp);
            if (onReceiveMessage != null) {
                onReceiveMessage.onReceive(dp);
            }
        } catch (Exception e) {
            System.out.println("Client thread: " + e.getMessage());
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
        run();
    }
}
