package sample;

import javafx.scene.control.Alert;

public class PopUpAlert {
    private String message;

    public PopUpAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
    }
}
