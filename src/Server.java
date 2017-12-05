package src;

import java.rmi.Naming;

/**
 * This class will act as the server that interacts
 * with all the clients. It will handle the creation
 * of new calendars and the creation of events.
 */
public class Server{

    //TODO: Test this?
    public static void main(String argv[]){
        try{
            System.setSecurityManager(new SecurityManager());
            System.out.println("Server: Registering Calendar Service");
            CalendarManager cm = new CalendarManagerImpl();
            Naming.rebind("CalendarService",cm);
            System.out.println("Server: Ready...");
        } catch (Exception e){
            System.out.println("Server-Error: " + e);
        }
    }

}