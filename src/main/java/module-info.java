module dambat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;

    opens dambat to javafx.fxml;
    opens dambat.controller to javafx.fxml;
    exports dambat;    
    exports dambat.controller;
    exports dambat.model;
}
