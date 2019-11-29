package emyu.learning.ui;

import emyu.learning.AppConstants;
import emyu.learning.LanChat;
import emyu.learning.models.Message;
import emyu.learning.network.MessageServer;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.net.InetAddress;
import java.util.Date;

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
        messagePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        messageContainer.setCellRenderer(this);
        add(messagePane);
        add(messageBox);
        messagePane.getViewport().setView(messageContainer);
    }

    public void setMessageServer(InetAddress receiverIp) {
        System.out.println("message server set");
        messageBox.setEnabled(true);
        messageBox.addActionListener((ActionEvent actionEvent) -> {
            boolean sent = (new MessageServer(parent.getSocket(), actionEvent.getActionCommand(), receiverIp)).send();
            if (sent) {
                Message message = new Message(parent.getUsername(), actionEvent.getActionCommand(), (new Date()).getTime(), Message.IncomingOrOutgoing.OUTGOING);
                ((DefaultListModel<Message>) messageContainer.getModel()).addElement(message);
            }
            messageBox.setText("");
        });
    }

    public void setMessageListModel(DefaultListModel<Message> listModel) {
        listModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent listDataEvent) {
                JScrollBar vertical = messagePane.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }

            @Override
            public void intervalRemoved(ListDataEvent listDataEvent) {
                System.out.println("intervalRemoved");
            }

            @Override
            public void contentsChanged(ListDataEvent listDataEvent) {
                System.out.println("contentsChanged");
            }
        });
        messageContainer.setModel(listModel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> jList, Message message, int i, boolean b, boolean b1) {
        JPanel messagePanel = new JPanel();
        messagePanel.setMinimumSize(new Dimension(getWidth(), 200));
        messagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppConstants.THEME_PURPLE));
        JLabel lblMsg = new JLabel(message.getBody());
        lblMsg.setHorizontalAlignment(JLabel.LEFT);
        messagePanel.add(lblMsg);
        return messagePanel;
    }
}
