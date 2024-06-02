# Airport Simulation

The Airport Simulation is a Java-based application that simulates the operation of multiple airports, runways, and flights. It demonstrates the coordination and synchronization of airport resources and provides real-time updates on flight statuses.

## Features

- Simulates multiple airports, each with a configurable number of runways.
- Allows users to specify the number of flights to simulate.
- Utilizes multithreading to simulate flights concurrently.
- Provides real-time updates on flight statuses, including departures, arrivals, and waiting times.
- Displays progress and status information through an interactive JavaFX user interface.

## Prerequisites

To run the Airport Simulation application, you need to have the following installed:

- Java Development Kit (JDK) 8 or above
- JavaFX SDK (included with JDK 8 and above)

## Getting Started

1. Clone the repository:
git clone https://github.com/GuyBernstein/Airport_Simulation

2. Open the project in your preferred Java IDE.

3. Make sure the JavaFX SDK is properly configured in your IDE.

4. Build and run the `AirportSimulation.java` file.

## Usage

1. Launch the Airport Simulation application.

2. Enter the desired number of airports, runways per airport, and flights in the provided input fields.

3. Click the "Start Simulation" button to begin the simulation.

4. The application will display real-time updates on flight statuses in the output list view.

5. The progress bar will indicate the overall progress of the simulation.

6. Click the "Clear" button to clear the output list view.

## FXML Layout

The user interface layout is defined in the `AirportSimulation.fxml` file using JavaFX FXML. The FXML file specifies the arrangement and properties of the UI components, such as labels, text fields, buttons, and list view. You can find the `AirportSimulation.fxml` file in the project repository.

## Code Structure

The project consists of the following main classes:

- `AirportSimulation.java`: The main application class that sets up the JavaFX stage and loads the FXML file.
- `AirportSimulationController.java`: The controller class that handles user input, starts the simulation, and updates the user interface.
- `AirportSimulationTask.java`: A task class that performs the actual simulation logic, creating airports, flights, and managing their coordination.
- `Airport.java`: Represents an airport with a name, available runways, and methods for flight departures and arrivals.
- `Flight.java`: Represents a flight with a flight number, departure airport, arrival airport, and simulation methods for departing, flying, and arriving.

## Acknowledgements

This project was developed as a learning exercise and demonstrates the use of multithreading, synchronization, and JavaFX in a simulation context.
