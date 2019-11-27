package emyu.learning.network;

import java.net.DatagramPacket;

public interface OnReceiveBroadcastListener {
    void onReceive(DatagramPacket dp);
}
