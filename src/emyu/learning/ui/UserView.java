package emyu.learning.ui;

import emyu.learning.AppConstants;
import emyu.learning.models.Message;
import emyu.learning.network.ClientThread;
import emyu.learning.network.OnReceiveMessage;

import javax.swing.*;
import java.awt.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;

public class UserView extends JLabel {
    private String name;
    private InetAddress ip;
    private UserPane parent;
    private DefaultListModel<Message> messages = new DefaultListModel<>();

    public UserView(String name, InetAddress ip, UserPane parent) {
        this.parent = parent;
        setName(name);
        setIp(ip);
        JLabel lblName = new JLabel(name);
        JLabel lblIp = new JLabel(ip == null ? "" : ip.toString());
        lblName.setFont(new Font("sans-serif", Font.BOLD, 16));
        lblName.setForeground(AppConstants.THEME_PURPLE);
        lblIp.setForeground(Color.GRAY);

        lblIp.setHorizontalAlignment(JLabel.RIGHT);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.DARK_GRAY), BorderFactory.createEmptyBorder(10,10,10,10)));

        add(lblName);
        add(lblIp);

        setBackground(Color.WHITE);
        setLayout(new GridLayout(1, 2));
        startClientThread();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public InetAddress getIp() {
        return ip;
    }

    public String toString() {
        return this.name + " (" + this.ip.toString() + ")";
    }

    public UserPane getParent() {
        return parent;
    }

    private void startClientThread() {
        ClientThread ct = new ClientThread(parent.getParent().getSocket(), ip, new OnReceiveMessage() {
            @Override
            public void onReceive(DatagramPacket dp) {
                String body = new String(dp.getData(), 0, dp.getLength());
                Message message = new Message(name, body, (new Date()).getTime(), Message.IncomingOrOutgoing.INCOMING);
                messages.addElement(message);
            }
        });
        ct.start();
    }

    public DefaultListModel getMessages() {
        return messages;
    }
}
