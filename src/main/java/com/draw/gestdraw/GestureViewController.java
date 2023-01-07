package com.draw.gestdraw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GestureViewController {
    @FXML
    private ImageView gestureView;
    @FXML
    private Button startStopButton;

    private List<File> files;
    private long duration;
    private boolean hasTimerStarted = false;
    private Timer timer;


    void initData(List<File> files, long duration) {
        this.files = files;
        this.duration = duration;

        startTimer();
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
