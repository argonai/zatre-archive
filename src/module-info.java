module guiModule {
    exports gui;
    exports main;
    exports exceptions;
    exports persistentie;
    exports domein;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.junit.jupiter.api;

    opens main to javafx.graphics, javafx.fxml;
    opens gui to javafx.fxml;
}