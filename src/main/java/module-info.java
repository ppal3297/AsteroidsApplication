module com.pankaj.asteriods{
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.pankaj.asteroids to javafx.fxml;
    exports com.pankaj.asteroids;
}