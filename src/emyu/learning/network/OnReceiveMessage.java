package emyu.learning.network;

import java.net.DatagramPacket;
import java.net.InetAddress;

public interface OnReceiveMessage {
    void onReceive(DatagramPacket dp);
}
