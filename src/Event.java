package src;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The Event class will contain information
 * about an event such as: Title, Name, Time,
 * and Attendees.
 * <p>
 * This class will need to be synchronous. The owner
 * may need to write to their calendar while another
 * user attempting to reading.
 */
public class Event implements Serializable {

    private String title;
    private Timestamp start;
    private Timestamp end;
    private Client owner;
    private List<Client> attendees;
    private boolean open;

    // True for public False for private
    private boolean type;
    private boolean passed;

    /**
     * @param title
     * @param start
     * @param end
     * @param owner
     * @param attendees
     * @param type
     * @param open
     */
    public Event(String title, Timestamp start, Timestamp end, Client owner, List<Client> attendees, boolean type, boolean open) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.owner = owner;
        this.attendees = attendees;
        this.type = type;
        this.open = open;
        this.passed = false;
    }

    public synchronized String getTitle() {
        return title;
    }

    public synchronized void setTitle(String title) {
        this.title = title;
    }

    public synchronized Timestamp getStart() {
        return start;
    }

    public synchronized void setStart(Timestamp start) {
        this.start = start;
    }

    public synchronized Timestamp getEnd() {
        return end;
    }

    public synchronized void setEnd(Timestamp end) {
        this.end = end;
    }

    public synchronized Client getOwner() {
        return owner;
    }

    public synchronized void setOwner(Client owner) {
        this.owner = owner;
    }

    public synchronized List<Client> getAttendees() {
        return attendees;
    }

    public synchronized void setAttendees(List<Client> attendees) {
        this.attendees = attendees;
    }

    public synchronized boolean isOpen() {
        return open;
    }

    public synchronized void setOpen(boolean open) {
        this.open = open;
    }

    public synchronized boolean isType() {
        return type;
    }

    public synchronized void setType(boolean type) {
        this.type = type;
    }

    public synchronized boolean hasPassed() {
        return this.passed;
    }

    public synchronized void setPassed(boolean val) {
        this.passed = val;
    }
}