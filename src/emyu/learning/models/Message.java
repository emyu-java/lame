package emyu.learning.models;

import java.util.Date;

public class Message {
    private String username;
    private String body;
    private Date time;
    private IncomingOrOutgoing inOrOut;

    public Message(String username,  String body, long time, IncomingOrOutgoing inOrOut) {
        setUsername(username);
        setBody(body);
        setTime(time);
        setInOrOut(inOrOut);
    }

    public void setUsername(String username) { this.username = username; }
    public void setBody(String body) { this.body = body; }
    public void setTime(long time) { this.time = new Date(time); }
    public void setInOrOut(IncomingOrOutgoing inOrOut) { this.inOrOut = inOrOut; }

    public String getUsername() { return username; }
    public String getBody() { return body; }
    public Date getTime() { return time; }
    public IncomingOrOutgoing getInOrOut() { return inOrOut; }

    public enum IncomingOrOutgoing { INCOMING, OUTGOING };
}
