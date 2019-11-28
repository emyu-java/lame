package emyu.learning.network;

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
            ds = new DatagramSocket(3000);
            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, 1024);
            ds.receive(dp);
            if (onReceiveMessage != null) {
                onReceiveMessage.onReceive(dp);
            }
//            String message = new String(dp.getData(), 0, dp.getLength());
//            System.out.println(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            ds.close();
        }
        run();
    }
}
