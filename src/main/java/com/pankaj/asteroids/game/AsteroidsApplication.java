package com.pankaj.asteroids.game;


import com.pankaj.asteroids.home.GameOverController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class AsteroidsApplication {

    public static int WIDTH = 600;
    public static int HEIGHT = 400;

    public AtomicInteger points;

    public void startGame(Stage primaryStage) throws Exception {
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        Text text = new Text(10, 20, "Points: 0");
        pane.getChildren().add(text);
        points = new AtomicInteger();


        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);

        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGHT));
            asteroids.add(asteroid);
        }
        List<Projectile> projectiles = new ArrayList<>();

        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> {
            pane.getChildren().add(asteroid.getCharacter());
        });

        Scene scene = new Scene(pane);


        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });


        new AnimationTimer() {
            public void handle(long now) {
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }
                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }
                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }

                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 7) {
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));
                    projectiles.add(projectile);

                    pane.getChildren().add(projectile.getCharacter());
                }


            ship.move();
                asteroids.forEach(asteroid -> asteroid.move());
                asteroids.forEach(
                        asteroid -> {
                            if (ship.collide(asteroid)) {
                                stop();
                                try {
                                    FXMLLoader loader = new FXMLLoader();
                                    loader.setLocation(getClass().getResource("/GameOver.fxml"));
                                    GameOverController controller = loader.getController();
                                    controller.setCurrent_score(points);
                                    Parent root = (Parent)loader.load();
                                    primaryStage.setScene(new Scene(root));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                        }
                );

                projectiles.forEach(projectile -> {
                    projectile.move();
                });

                List<Projectile> projectilesToRemove = projectiles.stream()
                        .map(projectile -> {
                            asteroids.forEach(asteroid -> {
                                if (asteroid.collide(projectile)) {
                                    text.setText("Points: " + points.addAndGet(1000));
                                }
                            });
                            return projectile;
                        }).filter(
                                projectile -> {
                                    List<Asteroid> collisions = asteroids.stream().filter(
                                            asteroid -> asteroid.collide(projectile)).collect(Collectors.toList());
                                    if (collisions.isEmpty()
                                    ) {
                                        return false;
                                    }
                                    collisions.stream().forEach(toBeremoved -> {
                                                asteroids.remove(toBeremoved);
                                                pane.getChildren().remove(toBeremoved.getCharacter());
                                            }
                                    );
                                    return true;
                                }).collect(Collectors.toList());

                projectilesToRemove.stream().forEach(projectile -> {
                    pane.getChildren().remove(projectile.getCharacter());
                    projectiles.remove(projectile);
                });
                if (Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
                    if (!asteroid.collide(ship)) {
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }


            }
        }.start();

        primaryStage.setTitle("Astroids!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


