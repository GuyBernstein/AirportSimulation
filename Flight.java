import java.util.Random;

public class Flight extends Thread {
    private int flightNum;
    private Airport departureAirport;
    private Airport arrivalAirport;

    //we add some static finals for the constant that are being shared between flight threads.
    private static final int FLY_TIME_MIN = 1;
    private static final int FLY_TIME_MAX = 2;
    private static final int DEPART_TIME_MIN = 2;
    private static final int DEPART_TIME_MAX = 5;
    private static final int LAND_TIME_MIN = 2;
    private static final int LAND_TIME_MAX = 5;
    private static final int SECOND_IN_MILLISECONDS = 1000;

    public Flight(int flightNum, Airport departureAirport, Airport arrivalAirport) {
        this.flightNum = flightNum;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
    }

    @Override
    public void run() {
        super.run();
        departFromAirport();
        flyToDestination();
        arriveAtAirport();
    }

    private void departFromAirport() {
        // Get a departure runway from the departure airport
        int departureRunway = getDepartureAirport().depart(getFlightNum());
        // Simulate the time taken for departure
        simulateActionTime(DEPART_TIME_MIN, DEPART_TIME_MAX, getFlightNum(),
                "departing from " + getDepartureAirport().getName());
        // Free the departure runway
        getDepartureAirport().freeRunway(getFlightNum(), departureRunway);
    }

    private void flyToDestination() {
        // Simulate the time taken for flying to the destination
        simulateActionTime(FLY_TIME_MIN, FLY_TIME_MAX, getFlightNum(),
                "flying from " + getDepartureAirport().getName() + " to " + getArrivalAirport().getName());
    }

    private void arriveAtAirport() {
        // Get a landing runway from the arrival airport
        int arrivalRunway = getArrivalAirport().land(getFlightNum());
        // Simulate the time taken for landing
        simulateActionTime(LAND_TIME_MIN, LAND_TIME_MAX, getFlightNum(),
                "landing at " + getArrivalAirport().getName());
        // Free the landing runway
        getArrivalAirport().freeRunway(getFlightNum(), arrivalRunway);
    }


    private Airport getArrivalAirport() {
        return arrivalAirport;
    }

    private Airport getDepartureAirport() {
        return departureAirport;
    }

    private int getFlightNum() {
        return flightNum;
    }

    private void simulateActionTime(int minTime, int maxTime, int flightNum, String action) {
        Random rnd = new Random();
        int time = rnd.nextInt(maxTime - minTime + 1) + minTime;// Generate a random time within the specified range
        try {
            Thread.sleep((long) time * SECOND_IN_MILLISECONDS);// Simulate the action by sleeping for the generated time
            // Notify the task that the flight completed the action
            String message = "Flight " + flightNum + " completed " + action + " in " + time + " seconds.";
            getDepartureAirport().notifyTask(message);
        } catch (InterruptedException e) {
            // Notify the task that the flight was interrupted while performing the action
            String message = "Flight " + flightNum + " was interrupted while " + action ;
            getDepartureAirport().notifyTask(message);
        }
    }
}