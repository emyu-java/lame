package emyu.learning.network;

import java.net.DatagramPacket;

public interface OnReceiveMessage {
    void onReceive(DatagramPacket dp);
}
