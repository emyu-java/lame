package emyu.learning.network;

import emyu.learning.AppConstants;
import java.net.*;

public class MessageServer {
    private DatagramSocket ds;
    private String message;
    private InetAddress ip;

    public MessageServer(String message, InetAddress ip) {
        this.message = message;
        this.ip = ip;
    }

    public boolean send() {
        try {
            ds = new DatagramSocket(AppConstants.MESSAGE_PORT);
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, AppConstants.MESSAGE_PORT);
            ds.send(dp);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }
}
