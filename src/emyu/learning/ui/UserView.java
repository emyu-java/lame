package emyu.learning.ui;

import emyu.learning.AppConstants;
import emyu.learning.models.Message;
import emyu.learning.network.ClientThread;
import emyu.learning.network.OnReceiveMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Date;

public class UserView extends JButton implements ActionListener {
    private String name;
    private InetAddress ip;
    private UserPane parent;

    public UserView(String name, InetAddress ip, UserPane parent) {
        this.parent = parent;
        setName(name);
        setIp(ip);
        JLabel lblName = new JLabel(name);
        JLabel lblIp = new JLabel(ip == null ? "" : ip.toString());
        lblName.setFont(new Font("sans-serif", Font.BOLD, 16));
        lblIp.setForeground(Color.GRAY);

        lblIp.setHorizontalAlignment(JLabel.RIGHT);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.DARK_GRAY), BorderFactory.createEmptyBorder(10,10,10,10)));

        add(lblName);
        add(lblIp);

//        setForeground(AppConstants.THEME_PURPLE);
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
        ClientThread ct = new ClientThread(new OnReceiveMessage() {
            @Override
            public void onReceive(DatagramPacket dp) {
                String body = new String(dp.getData(), 0, dp.getLength());
                Message message = new Message(name, dp.getAddress().toString(), body, (new Date()).getTime());
                if (message.save()) {
                    System.out.println("message saved: " + name + " (" + dp.getAddress().toString() + ")");
                } else {
                    System.out.println("could not save message: " + name + " (" + dp.getAddress().toString() + ")");
                }
            }
        });
        ct.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }


}
