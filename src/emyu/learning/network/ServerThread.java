package emyu.learning.network;

import java.io.IOException;
import java.net.*;

public class ServerThread extends Thread {
    DatagramSocket ds;
    private String message;

    public ServerThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        try {
            ds = new DatagramSocket();
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, 3000);
            ds.send(dp);
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            ds.close();
        }
    }
}
