package src.ui;

import src.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Util {

    private static Util instance;
    private CalendarManager cm;
    private Client owner;

    private Util() throws RemoteException {
        cm = CalendarManagerImpl.getInstance();
    }

    public static Util getInstance() {
        if (instance == null) {
            try {
                instance = new Util();
            } catch (RemoteException ex) {
                return null;
            }
        }
        return instance;
    }

    public ArrayList<Event> getEventList() {
        try {
            if(owner != null) {
                Calendar cal = cm.getCalendar(owner);
                ArrayList<Event> toReturn = new ArrayList<>();
                toReturn.addAll(cal.getEventList());
                return toReturn;
            } else
                return null;
        } catch (RemoteException e) {
            return null;
        }
    }

    public boolean scheduleEvent(Event e) {
        try {
            return owner != null && cm.getCalendar(owner).scheduleEvent(e.getOwner(), e.getAttendees(), e.getTitle(), e.getStart(), e.getEnd(), e.isType());
        } catch (RemoteException ex) {
            return false;
        }
    }

    public boolean insertOpenEvent(Event e) {
        try {
            return owner != null && cm.getCalendar(owner).insertOpenEvent(e.getOwner(), e.getStart(), e.getEnd(), e.isType());
        } catch (RemoteException ex) {
            return false;
        }
    }

    public boolean killClock(){
        try {
            Calendar cal = cm.getCalendar(owner);
            if(cal != null) {
                cal.killClock(owner);
                return true;
            } else
                return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean checkUser(String username){
        try {
            for(Client user : cm.allUsers()){
                if(user.getName().equals(username)) {
                    owner = user;
                    break;
                }
            }
            if(owner == null){
                owner = new ClientImpl(username);
            }
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

    public void setOwner(Client client) {
        this.owner = client;
    }

    public Client getOwner() {
        return this.owner;
    }
}
