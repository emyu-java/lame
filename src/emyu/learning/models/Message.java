package emyu.learning.models;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Message extends Db {
    private int id;
    private String username;
    private String ip;
    private String body;
    private Date receivedOn;
    private boolean read = false;

    public Message(String username, String ip, String body, long receivedOn) {
        setUsername(username);
        setIp(ip);
        setBody(body);
        setReceivedOn(receivedOn);
    }

    public Message(int id, String username, String ip, String body, long receivedOn, int read) {
        setId(id);
        setUsername(username);
        setIp(ip);
        setBody(body);
        setReceivedOn(receivedOn);
        setRead(read);
    }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setIp(String ip) { this.ip = ip; }
    public void setBody(String body) { this.body = body; }
    public void setReceivedOn(long receivedOn) { this.receivedOn = new Date(receivedOn); }
    public void setRead(int read) { this.read = read == 1; }

    public int getId() { return this.id; }
    public String getUsername() { return this.username; }
    public String getIp() { return this.ip; }
    public String getBody() { return this.body; }
    public Date getReceivedOn() { return this.receivedOn; }
    public boolean isRead() { return this.read; }

    /**
     * Inserts this message into the message table.
     *
     * @return true on success, false on failure
     */
    public boolean save() {
        boolean notFilled = username == null || ip == null || body == null;
        if (notFilled) {
            return false;
        }

        String sql = "insert into message (username, ip, message, receivedon)" +
                " values (?, ?, ?, ?)";
        try {
            prepare(sql);
            setString(1, getUsername());
            setString(2, getIp());
            setString(3, getBody());
            setLong(4, getReceivedOn().getTime());
            executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
