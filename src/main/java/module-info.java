module org.itproger.usercabinet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.itproger.usercabinet to javafx.fxml;
    exports org.itproger.usercabinet;
}