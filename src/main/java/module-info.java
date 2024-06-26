module com.pankaj.asteriods{
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    exports com.pankaj.asteroids.home;
    opens com.pankaj.asteroids.home to javafx.fxml;
    exports com.pankaj.asteroids.game;
    opens com.pankaj.asteroids.game to javafx.fxml;
}