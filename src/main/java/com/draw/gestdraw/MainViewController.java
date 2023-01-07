package com.draw.gestdraw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class MainViewController {
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    @FXML
    private TextField durationField;

    private List<File> files;

    @FXML
    protected void onSelectFolderClicked() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) durationField.getScene().getWindow();
        File selectedFolder = directoryChooser.showDialog(stage);
        files = new ArrayList<>(List.of(Objects.requireNonNull(selectedFolder.listFiles())));
    }

    @FXML
    protected void onStartClicked(ActionEvent event) throws IOException {
        if (files != null && isDurationSet()) {
            switchToGestureView(event);
        }
    }

    public void switchToGestureView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("views/gesture.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        GestureViewController controller = loader.getController();
        controller.initData(files, Long.parseLong(durationField.getText()) * 1000);
        stage.show();
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private boolean isDurationSet() {
        return !durationField.getText().isBlank() && isNumeric(durationField.getText());
    }
}