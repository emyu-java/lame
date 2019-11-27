package emyu.learning.ui;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;

public class UserView extends JButton {
    private String name;
    private InetAddress ip;

    public UserView(String name, InetAddress ip) {
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

        setBackground(Color.WHITE);
        setLayout(new GridLayout(1, 2));
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
}
