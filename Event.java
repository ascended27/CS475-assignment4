import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The Event class will contain information
 * about an event such as: Title, Name, Time,
 * and Attendees.
 *
 * This class will need to be synchronous. The owner
 * may need to write to their calendar while another
 * user attempting to reading.
 */
public class Event {

    private String title;
    private Timestamp start;
    private Timestamp end;
    private User owner;
    private List<User> attendees;
    private boolean open;

    // True for public False for private
    private boolean type;

    public Event(String title, Timestamp start, Timestamp end, User owner, List<User> attendees,boolean type, boolean open) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.owner = owner;
        this.attendees = attendees;
        this.type = type;
        this.open = open;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<User> attendees) {
        this.attendees = attendees;
    }


    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}