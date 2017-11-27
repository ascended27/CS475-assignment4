/*
 * The Calendar class represents the calendar object
 * that each user uses to schedule events. This class
 * will store the user that owns the calendar and a
 * list of events that the user is attending.
 *
 * This class will need to be synchronus. The
 * CalendarManager will need to be able read
 * and write to a calendar from different
 * threads. The owner may need to write to
 * their calendar while another user is reading.
 *
 * ------- From the PDF ------------------------------
 * Each Calendar object maintains a database of events
 * for its user. As such, it should support the following
 * classes of services that essentially query and modify its database
 * and that of other users. Optional parameters appear in square
 * brackets.
 *
 * Retrieve Event [usertime-range]
 * Retrieve the schedule of a user for the specified
 * time-range. If user is omitted, it defaults to the
 * owner of the calendar. The Calendar object will
 * need to communicate with another object to
 * retrieve a schedule if it does not maintain the
 * calendar of the specified user. Note that the calendar
 * only returns events that the requesting user has
 * privileges to view.
 *
 * Schedule Event [user-list event]
 * Schedule an event in calendars of each user
 * specified in user-list. If user-list is omitted,
 * schedule the event in the local calendar.
 * A group event may be scheduled in the calendar of each proposed
 * user only if the Access Control field of the respective event in
 * every specified calendar is currently set to Open
 * ---------------------------------------------------
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.Timestamp;
import java.util.List;

public class CalendarImpl extends UnicastRemoteObject implements Calendar {

    protected CalendarImpl() throws RemoteException {
    }

    /**
     * Retrieves event for passed user with passed start and end
     *
     * @param user  An attendee
     * @param start
     * @param end
     * @return The event
     */
    public Event retrieveEvent(User user, Timestamp start, Timestamp end) throws RemoteException{
        return null;
    }

    /**
     * Schedules an event with a list of attendees specified by users
     * @param users
     * @param event
     * @return If the schedule is inserted successfully
     */
    public boolean scheduleEvent(List<User> users, Event event) throws RemoteException{
        return false;
    }
}