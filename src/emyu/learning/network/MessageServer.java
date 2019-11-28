package emyu.learning.network;

import emyu.learning.AppConstants;
import java.net.*;

public class MessageServer {
    private DatagramSocket ds;
    private String message;
    private InetAddress ip;

    public MessageServer(DatagramSocket ds, String message, InetAddress ip) {
        this.ds = ds;
        this.message = message;
        this.ip = ip;
    }

    public boolean send() {
        try {
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, AppConstants.MESSAGE_PORT);
            if (ds != null) {
                ds.send(dp);
            } else {
                System.out.println("server: no socket");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
