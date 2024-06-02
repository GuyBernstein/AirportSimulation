import javafx.application.Platform;
import javafx.concurrent.Task;
import java.util.Random;

public class AirportSimulationTask extends Task<String> {
    private int numAirports;
    private int numRunways;
    private int numFlights;
    private static final double SIMULATION_COMPLETED = 1.0;


    public void setSimulationParameters(int numAirports, int numRunways, int numFlights) {
        this.numAirports = numAirports;
        this.numRunways = numRunways;
        this.numFlights = numFlights;
    }

    @Override
    protected String call() throws Exception {
        // Create airports and flights based on the simulation parameters
        Airport[] airports = createAirports(numAirports, numRunways);
        Flight[] flights = createFlights(airports, numFlights);

        // Start all flights
        startFlights(flights);
        // Wait for all flights to complete
        waitForFlights(flights);


        // Create the final message
        String finalMessage = "All flights have completed.";
        // Set progress to 1.0 when completed
        updateProgress(SIMULATION_COMPLETED, SIMULATION_COMPLETED);

        return finalMessage;
    }

    private Airport[] createAirports(int numAirports, int numRunways) {
        Airport[] airports = new Airport[numAirports];
        for (int i = 0; i < numAirports; i++) {
            char airportLetter = (char) ('A' + i);// Assign airport names as A, B, C, etc.
            String airportName = "Airport " + airportLetter;
            // Create a new airport with the assigned name, number of runways, and the simulation task
            airports[i] = new Airport(airportName, numRunways, this);
        }
        return airports;
    }

    private Flight[] createFlights(Airport[] airports, int numFlights) {
        Random rnd = new Random();
        Flight[] flights = new Flight[numFlights];
        for (int i = 0; i < numFlights; i++) {
            // Select a random departure airport
            int departureIndex = rnd.nextInt(airports.length);
            int arrivalIndex;
            do {
                // Select a random arrival airport
                arrivalIndex = rnd.nextInt(airports.length);
            } while (arrivalIndex == departureIndex);// Ensure the flight direction is valid (departure != arrival)


            Airport departureAirport = airports[departureIndex];
            Airport arrivalAirport = airports[arrivalIndex];

            // Create flights with numbers 1, 2, 3, etc.
            flights[i] = new Flight(
                    i + 1, departureAirport, arrivalAirport);
        }
        return flights;
    }

    private void startFlights(Flight[] flights) {
        for (int i = flights.length - 1; i >= 0; i--) {// Start flights in reverse order
            flights[i].start();
        }
    }

    private void waitForFlights(Flight[] flights) {
        for (Flight flight : flights) {
            try {
                // Wait for each flight to complete
                flight.join();
            } catch (InterruptedException e) {
                // Append a message if the task is interrupted
                appendMessage("task was interrupted");
            }
        }
    }

    public void appendMessage(String message) {
        Platform.runLater(() -> {
            updateMessage(message);
            updateValue(message); // Update the value property with the current message
        });
    }
}