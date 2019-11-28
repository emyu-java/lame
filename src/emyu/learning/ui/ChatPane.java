package emyu.learning.ui;

import emyu.learning.AppConstants;
import emyu.learning.LanChat;
import emyu.learning.network.MessageServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.InetAddress;

public class ChatPane extends JPanel {
    private LanChat parent;
    private JTextField messageBox = new JTextField();
    private SpringLayout layout = new SpringLayout();

    /**
     * The constructor
     */
    public ChatPane(LanChat parent) {
        this.parent = parent;
        layout.putConstraint(SpringLayout.WEST, messageBox, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, messageBox, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, messageBox, 0, SpringLayout.SOUTH, this);
        setLayout(layout);
        //messageBox.setBorder(BorderFactory.createLineBorder(AppConstants.THEME_PURPLE));
        messageBox.setMargin(new Insets(5, 10, 5, 10));
        messageBox.setForeground(AppConstants.THEME_PURPLE);
        messageBox.setEnabled(false);

        add(messageBox);
    }

    public void setMessageServer(InetAddress receiverIp) {
        System.out.println("message server set");
        messageBox.setEnabled(true);
        messageBox.addActionListener((ActionEvent actionEvent) -> {
            (new MessageServer(parent.getSocket(), actionEvent.getActionCommand(), receiverIp)).send();
            messageBox.setText("");
        });
    }
}
