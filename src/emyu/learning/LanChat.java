package emyu.learning;

import emyu.learning.network.ClientThread;
import emyu.learning.ui.ChatPane;
import emyu.learning.ui.UserPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.*;
import java.util.Enumeration;

public class LanChat extends JFrame {
    private UserPane userPane = new UserPane(this);
    private ChatPane chatPane = new ChatPane();
    private InetAddress broadcastIp;
    private InetAddress localIp;
    private String username;

    /**
     * The constructor.
     */
    private LanChat() {
        setLayout(null);
        userPane.setBounds(0, 0, 200, 700);
        chatPane.setBounds(201, 2, 495, 665);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage((new ImageIcon("icon.png")).getImage());
        setTitle(AppConstants.APP_TITLE);
        setSize(700, 700);
        setBackground(Color.WHITE);
        setResizable(false);
        setVisible(true);
        add(userPane);
        add(chatPane);
        initialize();
        userPane.startReceivingBroadCast();
    }

    private void initialize() {
        try {
            Enumeration<NetworkInterface> nfaces = NetworkInterface.getNetworkInterfaces();
            while (nfaces.hasMoreElements()) {
                NetworkInterface nface = nfaces.nextElement();
                java.util.List<InterfaceAddress> ips = nface.getInterfaceAddresses();
                for (InterfaceAddress a : ips) {
                    if (a.getBroadcast() != null) {
                        broadcastIp = a.getBroadcast();
                        localIp = a.getAddress();
                    }
                }
            }
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(this, "Could not initialize network connections. Closing the application now.", "Sorry", JOptionPane.ERROR_MESSAGE);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    public InetAddress getLocalIp() {
        return this.localIp;
    }

    public InetAddress getBroadcastIp() {
        return this.broadcastIp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public ChatPane getChatPane() {
        return chatPane;
    }

    /**
     * main method to launch the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        LanChat c = new LanChat();
    }
}