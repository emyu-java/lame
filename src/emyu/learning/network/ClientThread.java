package emyu.learning.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientThread extends Thread {
    DatagramSocket ds;
    @Override
    public void run() {
        while (true) {
            try {
                ds = new DatagramSocket(3000);
                byte[] buf = new byte[1024];
                DatagramPacket dp = new DatagramPacket(buf, 1024);
                ds.receive(dp);
                String message = new String(dp.getData(), 0, dp.getLength());
                System.out.println(message);
            } catch (SocketException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                ds.close();
            }
        }
    }
}
