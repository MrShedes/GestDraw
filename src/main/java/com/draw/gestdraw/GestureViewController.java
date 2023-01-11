package com.draw.gestdraw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GestureViewController {
    @FXML
    private ImageView gestureView;
    @FXML
    private Button startStopButton;
    @FXML
    private AnchorPane imageViewParent;

    private List<File> files;
    private long duration;
    private boolean hasTimerStarted = false;
    private Timer timer;


    void initData(List<File> files, long duration) {
        this.files = files;
        this.duration = duration;

        startTimer();
        gestureView.fitHeightProperty().bind(imageViewParent.heightProperty());
    }

    @FXML
    protected void onStartStopClicked() {
        if (hasTimerStarted) {
            timer.cancel();
            hasTimerStarted = false;
            startStopButton.setText("Start");
        }
        else {
            hasTimerStarted = true;
            startStopButton.setText("Stop");
            startTimer(duration/2);
        }
    }

    @FXML
    protected void onNextClicked() {
        if (hasTimerStarted) {
            timer.cancel();
            startTimer();
        }
    }

    @FXML
    protected void onBackClicked(ActionEvent event) throws IOException {
        if (hasTimerStarted) {
            timer.cancel();
        }

        FXMLLoader loader = new FXMLLoader(Application.class.getResource("views/main.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    private void showRandomFile() {
        int index = (int)Math.floor(Math.random() * files.size());

        Image image = new Image(files.get(index).getAbsolutePath());
        gestureView.setImage(image);
        files.remove(index);
        System.out.println(files.size());
    }

    private void startTimer() {
        startTimer(0);
    }

    private void startTimer(long delay) {
        hasTimerStarted = true;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                showRandomFile();
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, delay, duration);
    }
}
