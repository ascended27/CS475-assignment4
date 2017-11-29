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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CalendarImpl extends UnicastRemoteObject implements Calendar {

    public User owner;
    private Thread clockThread;
    private ArrayList<Event> eventList;

    protected CalendarImpl(User owner) throws RemoteException {
        this.owner = owner;
    }

    /**
     * Retrieves event for passed user with passed start and end
     *
     * @param user  An attendee
     * @param start The starting time of the event to retrieve
     * @param end   The ending time of the event to retrieve
     * @return The event
     */
    public Event retrieveEvent(User user, Timestamp start, Timestamp end) throws RemoteException {
        Event toReturn = null;
        for (Event event : eventList) {
            if (event.getStart().equals(start) && event.getEnd().equals(end)) {
                toReturn = event;
                break;
            }
        }
        return toReturn;
    }

    /**
     * Schedules an event with a list of attendees specified by users
     *
     * @param owner     The owner of the new event
     * @param attendees The list of users that are attending
     * @param title     The name of the new event
     * @param start     The start time of the new event
     * @param stop      The end time of the new event
     * @param type      Is the event public or private
     * @return True if the event was scheduled, otherwise false
     * @throws RemoteException If the connection was lost
     */
    //TODO: Test this
    public boolean scheduleEvent(User owner, List<User> attendees, String title, Timestamp start, Timestamp stop, boolean type) throws RemoteException {
        boolean canSchedule = false;
        ArrayList<Calendar> calendars = new ArrayList<>();

        /*
         * If owner is the owner of this calendar then we are creating a new event for that user.
         * If there are any attendees then we need to check to see if they are available for this event.
         *      If they are then flip a flag saying everyone is good.
         * For each event x in this calendar. Check to see if there are any conflicting events with this one.
         *      If there are then return false. This event can't be scheduled.
         *      Otherwise if there are attendees and everyone is available then call this method on their calendars
         *          with the same parameters. Then check this calendar's open events. If there is an open event that has
         *          a start before this new event and an end after then split that open event and insert this new event
         *          between. Otherwise the event would have to have the exact time range as the new event. So just copy
         *          over the params for this call to that event.
         */

        // Find an open event
        Event found = null;
        for (Event event : eventList) {
            if (event.isOpen() && event.getStart().compareTo(start) >= 0 && event.getEnd().compareTo(stop) <= 0) {
                found = event;
                break;
            }
        }
        // If found is null then there is no open event
        if (found == null)
            return false;

        // If we have attendees we are in a group event.
        // If this calendar owner is the same as the event owner then we are need to invite the attendees
        if (attendees.size() != 0 && owner.getName().equals(this.owner.getName())) {
            CalendarManager cm = new CalendarManagerImpl();

            for (User u : attendees) {
                calendars.add(cm.getCalendar(u));
            }

            for (Calendar cal : calendars) {
                for (Event event : cal.getEventList()) {
                    // If the event is open and it starts before or the same time as the new event
                    // and it ends after or the same time as the new event
                    if (event.isOpen() && event.getStart().compareTo(start) <= 0 && event.getEnd().compareTo(stop) >= 0) {
                        canSchedule = true;
                    } else {
                        canSchedule = false;
                        break;
                    }
                }
                if (!canSchedule) {
                    // If canSchedule == false then this user is not available so abandon the event.
                    break;
                }
            }
        }

        if (canSchedule) {
            for (Calendar cal : calendars) {
                cal.scheduleEvent(owner, attendees, title, start, stop, type);
            }
        }

        // If the event is open and matches the time range then just use this event
        if (found.isOpen() && found.getStart().equals(start) && found.getEnd().equals(stop)) {
            // Copy the values to the event
            found.setOwner(owner);
            found.setTitle(title);
            found.setOpen(false);
            found.setType(type);
            found.setAttendees(attendees);
            return true;
        }

        // If the event is open and has a start before the new event and a stop after the new event then split it
        else if (found.isOpen() && found.getStart().compareTo(start) > 0 && found.getEnd().compareTo(stop) < 0) {
            // Create two new events that are a split between the event and the new event
            Event before = new Event(found.getTitle(), found.getStart(), start, found.getOwner(), null, true, found.isType());
            Event after = new Event(found.getTitle(), stop, found.getEnd(), found.getOwner(), null, true, found.isType());
            // Remove the old event
            eventList.remove(found);
            // Insert the split open event
            eventList.add(before);
            eventList.add(after);
            // Add the new event
            eventList.add(new Event(title, start, stop, owner, attendees, false, type));
            return true;
        }

        return false;
    }

    /**
     * @param owner The owner of the event to schedule, should match the owner of this calendar
     * @param start The start time of the open event
     * @param stop  The end time of the open event
     * @return True if the event was scheduled, otherwise false
     * @throws RemoteException If the connection was lost
     */
    @Override
    public boolean insertOpenEvent(User owner, Timestamp start, Timestamp stop, boolean type) throws RemoteException {

        /*
        * For each event x in the event list check to see:
        *       if x.start is within start and stop. If it is then
        *           return false. The events conflict.
        *       if x.end is within start and stop. If it is then
        *           return false. The events conflict.
        */

        for (Event event : eventList) {
            // If event is within start and stop then we can't create an event
            if (event.getStart().compareTo(start) < 0 && event.getStart().compareTo(stop) < 0 ||
                    event.getEnd().compareTo(start) > 0 && event.getEnd().compareTo(stop) > 0) {
                eventList.add(new Event("", start, stop, owner, null, type, true));
                return true;
            }
        }

        return false;
    }

    /**
     * Used to retrieve the event list when another calendar is trying to schedule a group event
     *
     * @return The list of events
     */
    public List<Event> getEventList() {
        return eventList;
    }

    /**
     * Used to retrieve the owner of the calendar
     *
     * @return The user that owns the calendar
     */
    public User getOwner() {
        return owner;
    }

    public boolean startClock(User owner) {
        if (owner.getName().equals(this.owner.getName())) {
            clockThread = new Clock(this);
            clockThread.start();
            return true;
        }
        return false;
    }
}