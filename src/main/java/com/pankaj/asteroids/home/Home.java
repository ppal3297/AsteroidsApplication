package com.pankaj.asteroids.home;

import com.pankaj.asteroids.game.AsteroidsApplication;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Home extends Application {

    public Button start_game_btn;
    public Button exit_game_btn;

    @Override
    public void start(Stage window) throws Exception {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            window.setScene(new Scene(root));
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle_start_game(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        Object source = event.getSource();
        if(source instanceof Button){
            Scene scene = ((Button) source).getScene();
            stage = (Stage) scene.getWindow();
        }
        AsteroidsApplication application = new AsteroidsApplication();
        application.startGame(stage);

    }

    public void handle_exit_game(ActionEvent event){
        event.consume();
        Object source = event.getSource();
        if(source instanceof Button){
            Scene scene = ((Button) source).getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

