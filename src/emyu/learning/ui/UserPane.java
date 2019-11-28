package emyu.learning.ui;

import javax.swing.*;
import java.awt.*;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.List;

import emyu.learning.AppConstants;
import emyu.learning.LanChat;
import emyu.learning.network.BroadcastServerThread;

public class UserPane extends JScrollPane {
    private JPanel container = new JPanel();
    private JViewport viewport;
    private LanChat parent;
    private HashMap<InetAddress, UserView> users = new HashMap<>();

    public UserPane(LanChat parent) {
        this.parent = parent;
        viewport = getViewport();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        //container.add(new UserView("emyu10", null));
        viewport.setMinimumSize(new Dimension(200, parent.getHeight()));
        viewport.setMaximumSize(new Dimension(200, parent.getHeight()));
        viewport.add(container);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        JLabel headerLabel = new JLabel("Online Users");
        headerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//        headerLabel.setForeground(AppConstants.THEME_PURPLE);
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        setColumnHeaderView(headerLabel);
    }

    public void startReceivingBroadCast() {
        String username = JOptionPane.showInputDialog(parent, "Enter your preferred username");
        parent.setUsername(username);

        // construct a UserView with broadcast IP because the server needs to send to the broadcast ip
        (new BroadcastServerThread(new UserView(parent.getUsername(), parent.getBroadcastIp(), this))).start();

        BroadcastClient broadcastClient = new BroadcastClient(this);
        broadcastClient.execute();
    }

    public LanChat getParent() {
        return parent;
    }


    /**
     *
     */
    class BroadcastClient extends SwingWorker<Void, DatagramPacket> {
        private UserPane userViewParent;

        BroadcastClient(UserPane parent) {
            this.userViewParent = parent;
        }

        @Override
        protected Void doInBackground() throws Exception {
            while (true) {
                MulticastSocket ds = new MulticastSocket(AppConstants.BROADCAST_PORT);
                byte[] buf = new byte[1024];
                DatagramPacket dp = new DatagramPacket(buf, 1024);
                ds.receive(dp);
                if (!dp.getAddress().equals(userViewParent.getParent().getLocalIp())) {
                    publish(dp);
                }
            }
        }

        @Override
        protected void process(List<DatagramPacket> chunks) {
            for (DatagramPacket dp: chunks) {
                String name = new String(dp.getData(), 0, dp.getLength());
                InetAddress ip  = dp.getAddress();
                UserView user = new UserView(name, ip, userViewParent);
                if (!users.containsKey(ip)) {
                    users.put(ip, user);
                    container.add(user);
                    container.updateUI();
                    System.out.println(user);
                }
            }
        }
    }
}
