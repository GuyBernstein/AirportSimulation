import java.util.*;

public class Airport {
    private String name;
    private List<Integer> availableRunways;
    private int numRunways;
    private AirportSimulationTask task;
    private final int FIRST_RUNWAY = 0;

    public Airport(String name, int numRunways, AirportSimulationTask task) {//the constructor that i use
        this.name = name;
        this.availableRunways = new ArrayList<Integer>();
        this.numRunways = numRunways;
        for (int i = 0; i < numRunways; i++) {
            availableRunways.add(i+1); // Add all runway numbers to the queue
        }
        this.task = task;
    }

    private synchronized int assignRunway(int flightNum, String action) {
        while (availableRunways.isEmpty()) {
            try {
                // Notify the task that the flight is waiting for a free runway
                String message = "Flight " + flightNum + " is waiting for a free runway at " + getName() + ".";
                notifyTask(message);
                wait();// Wait until a runway becomes available
            } catch (InterruptedException e) {
                // Notify the task that the flight was interrupted while waiting for a runway
                String message = "Flight " + flightNum + " was interrupted, thus not " + action + " " + getName() + ".";
                notifyTask(message);
            }
        }
        int runway = availableRunways.remove(FIRST_RUNWAY);// Assign the first available runway

        // Notify the task that the flight got a runway and is performing the action
        String assignedMessage = "Flight " + flightNum + " got runway " + runway + " and is " + action + " " + getName() + ".";
        notifyTask(assignedMessage);
        return runway;
    }


    public int depart(int flightNum) {
        // Assign a runway for the flight to depart
        return assignRunway(flightNum, "approaching to depart from");
    }

    public int land(int flightNum) {
        // Assign a runway for the flight to land
        return assignRunway(flightNum, "approaching to land in");
    }
    public synchronized void freeRunway(int flightNum, int runway) {
        // Notify the task that the flight has freed the runway
        String message = "Flight " + flightNum + " has freed Runway " + runway + " at " + getName() + ".";
        notifyTask(message);
        // Add the runway back to the list of available runways
        availableRunways.add(runway);
        notify();// Notify only one waiting flight to maintain the order of attending flights
    }

    public String getName() {
        return name;
    }

    public void notifyTask(String message) {
        task.appendMessage(message);
    }
}