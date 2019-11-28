package emyu.learning.ui;

import emyu.learning.network.ServerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChatPane extends JPanel {
    private JTextField messageBox = new JTextField();
    private SpringLayout layout = new SpringLayout();

    /**
     * The constructor
     */
    public ChatPane() {
        setLayout(layout);
        layout.putConstraint(SpringLayout.WEST, messageBox, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, messageBox, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, messageBox, 0, SpringLayout.SOUTH, this);
        messageBox.setMargin(new Insets(5, 10, 5, 10));
        messageBox.addActionListener((ActionEvent actionEvent) -> {
            (new ServerThread(actionEvent.getActionCommand())).start();
//            System.out.println(actionEvent.getSource());
            messageBox.setText(null);
        });
        add(messageBox);
    }
}
