package com.pankaj.asteroids.home;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicInteger;

public class GameOverController {



    @FXML
    public Label current_score;

    public void setCurrent_score(AtomicInteger points){
        current_score.setText(String.valueOf(points.get()));
    }


}
