package emyu.learning.network;

import emyu.learning.AppConstants;
import emyu.learning.ui.UserView;

import java.net.*;

public class BroadcastServerThread extends Thread {
    private MulticastSocket ds;
    private InetAddress ip;
    private String username;

    public BroadcastServerThread(UserView user) {
        this.username = user.getName();
        this.ip = user.getIp();
    }

    @Override
    public void run() {
        while (true) {
            try {
                int port = AppConstants.BROADCAST_PORT;
                ds = new MulticastSocket(port);
                String broadcastMessage = this.username;
                DatagramPacket dp = new DatagramPacket(broadcastMessage.getBytes(), broadcastMessage.length(), ip, port);
                ds.send(dp);
                Thread.sleep(1000);
//                System.out.println("server running on: " + ip + ":" + port);
            } catch (Exception e) {
                System.out.println("from broadcast server: " + e.getMessage());
            } finally {
                if (ds != null)
                ds.close();
            }
        }
    }
}
