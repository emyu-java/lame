package emyu.learning.ui;

import emyu.learning.AppConstants;
import emyu.learning.LanChat;
import emyu.learning.models.Message;
import emyu.learning.network.MessageServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.InetAddress;

public class ChatPane extends JPanel  implements ListCellRenderer<Message> {
    private LanChat parent;
    private JTextField messageBox = new JTextField();
    private JScrollPane messagePane = new JScrollPane();
    private JList<Message> messageContainer = new JList();
    private SpringLayout layout = new SpringLayout();


    /**
     * The constructor
     */
    public ChatPane(LanChat parent) {
        this.parent = parent;
        layout.putConstraint(SpringLayout.WEST, messageBox, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, messageBox, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, messageBox, 0, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.NORTH, messagePane, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, messagePane, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, messagePane, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, messagePane, 0, SpringLayout.NORTH, messageBox);
        setLayout(layout);

        //messageBox.setBorder(BorderFactory.createLineBorder(AppConstants.THEME_PURPLE));
        messageBox.setMargin(new Insets(5, 10, 5, 10));
        messageBox.setForeground(AppConstants.THEME_PURPLE);
        messageBox.setEnabled(false);

        messagePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        messageContainer.setCellRenderer(this);
        add(messagePane);
        add(messageBox);
        messagePane.getViewport().setView(messageContainer);
    }

    public void setMessageServer(InetAddress receiverIp) {
        System.out.println("message server set");
        messageBox.setEnabled(true);
        messageBox.addActionListener((ActionEvent actionEvent) -> {
            (new MessageServer(parent.getSocket(), actionEvent.getActionCommand(), receiverIp)).send();
            messageBox.setText("");
        });
    }

    public void setMessageListModel(DefaultListModel<Message> listModel) {
        messageContainer.setModel(listModel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> jList, Message message, int i, boolean b, boolean b1) {
        JPanel messagePanel = new JPanel();
        messagePanel.setMinimumSize(new Dimension(getWidth(), 200));
        messagePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(AppConstants.THEME_PURPLE, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JLabel lblMsg = new JLabel(message.getBody());
        messagePanel.add(lblMsg);
        return messagePanel;
    }
}
