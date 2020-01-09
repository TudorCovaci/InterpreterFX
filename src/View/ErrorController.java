package View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorController {
    @FXML
    private Label errorLabel;

    @FXML
    private Button closeButton;


    private Stage errorStage;

    public ErrorController(String message) throws IOException {
        errorStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("errorWindow.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        errorStage.setScene(scene);
        errorStage.setTitle("Error");
        errorLabel.setText(message);
        closeButton.setOnAction(event -> errorStage.close());

    }

    public void showStage() {
        errorStage.showAndWait();
    }
}
