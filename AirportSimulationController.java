import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AirportSimulationController {
    @FXML
    private TextField numAirportsField;
    @FXML
    private TextField numRunwaysField;
    @FXML
    private TextField numFlightsField;
    @FXML
    private ListView<String> outputListView;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label statusLabel;

    // stores the list of messages of the flights received from AirportSimulationTask
    private ObservableList<String> messages =
            FXCollections.observableArrayList();
    private AirportSimulationTask task;

    // binds outputListView's items to the ObservableList messages
    @FXML
    private void initialize() {
        outputListView.setItems(messages);
    }

    @FXML
    private void startSimulation(ActionEvent event) {
        messages.clear();
        statusLabel.setStyle("-fx-text-fill: black;"); // default is label color to black

        try {
            int numAirports = Integer.parseInt(numAirportsField.getText());
            int numRunways = Integer.parseInt(numRunwaysField.getText());
            int numFlights = Integer.parseInt(numFlightsField.getText());

            task = new AirportSimulationTask();// create task with parameters
            task.setSimulationParameters(numAirports, numRunways, numFlights);

            // update progressBar based on task's progressProperty
            progressBar.progressProperty().bind(task.progressProperty());

            // store intermediate results in the ObservableList messages
            task.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {//task returns that the flights are done when it terminates
                    messages.add(newValue);
                    outputListView.scrollTo(
                            outputListView.getItems().size());
                }
            });

            task.setOnRunning((runningEvent) -> {
                statusLabel.setText("Simulating...");
            });

            task.setOnSucceeded((succeededEvent) -> {
                statusLabel.setText("Finished simulating.");
            });


            // create ExecutorService to manage threads
            ExecutorService executorService =
                    Executors.newFixedThreadPool(1);//for the task
            executorService.execute(task); // start the task
            executorService.shutdown();
        } catch (NumberFormatException e) {
            showErrorMessage("Invalid input. Please enter valid integers.");
        }
    }

    @FXML
    private void clearMessages(ActionEvent event) {
        messages.clear();
    }
    private void showErrorMessage(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: red;");
    }
}