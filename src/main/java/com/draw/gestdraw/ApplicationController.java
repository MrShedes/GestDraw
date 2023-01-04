package com.draw.gestdraw;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

public class ApplicationController {
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    @FXML
    private ImageView gestureView;
    @FXML
    private TextField durationField;

    private List<File> files;
    private boolean hasTimerStarted = false;
    private Timer timer;

    @FXML
    protected void onSelectFolderClicked() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) gestureView.getScene().getWindow();
        File selectedFolder = directoryChooser.showDialog(stage);
        files = new ArrayList<>(List.of(Objects.requireNonNull(selectedFolder.listFiles())));
    }

    @FXML
    protected void onStartClicked() {
        if (files != null && !hasTimerStarted && isDurationSet()) {
            startTimer();
        }
    }

    @FXML
    protected void onStopClicked() {
        if (hasTimerStarted) {
            timer.cancel();
            hasTimerStarted = false;
        }
    }

    @FXML
    protected void onNextClicked() {
        if (hasTimerStarted) {
            timer.cancel();
            startTimer();
        }
    }

    private void showRandomFile() {
        int index = (int)Math.floor(Math.random() * files.size());

        Image image = new Image(files.get(index).getAbsolutePath());
        gestureView.setImage(image);
        files.remove(index);
        System.out.println(files.size());
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

    private void startTimer() {
        hasTimerStarted = true;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                showRandomFile();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, Long.parseLong(durationField.getText()) * 1000);
    }
}