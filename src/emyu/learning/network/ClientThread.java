package emyu.learning.network;

import emyu.learning.AppConstants;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientThread extends Thread {
    private DatagramSocket ds;

    private OnReceiveMessage onReceiveMessage;
    private InetAddress remoteIp;
    byte[] buf = new byte[1024];
    private DatagramPacket dp = new DatagramPacket(buf, 1024);

    public ClientThread(DatagramSocket ds, InetAddress remoteIp, OnReceiveMessage onReceiveMessage) {
        this.ds = ds;
        this.remoteIp = remoteIp;
        setOnReceiveMessage(onReceiveMessage);
    }

    public void setOnReceiveMessage(OnReceiveMessage onReceiveMessage) {
        this.onReceiveMessage = onReceiveMessage;
    }

    @Override
    public void run() {
        try {
            if (ds != null) {
                ds.receive(dp);
                if (onReceiveMessage != null) {
                    onReceiveMessage.onReceive(dp);
                }
            }
        } catch (Exception e) {
            System.out.println("Client thread: " + e.getMessage());
        }
        run();
    }
}
