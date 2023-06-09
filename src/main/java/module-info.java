module com.thelibrary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.controlsfx.controls;
    requires org.jetbrains.annotations;
    requires mysql.connector.java;
    requires java.sql.rowset;
    requires java.mail;


    exports com.thelibrary.ui;
    opens com.thelibrary.controller to javafx.fxml;
    exports com.thelibrary.controller;
    opens com.thelibrary to javafx.fxml;
    exports com.thelibrary;
    exports com.thelibrary.util;
    opens com.thelibrary.util to javafx.fxml;
    exports com.thelibrary.models;
    opens com.thelibrary.models;
    exports com.thelibrary.controller.Member;
    opens com.thelibrary.controller.Member to javafx.fxml;
    exports com.thelibrary.controller.Assistant;
    opens com.thelibrary.controller.Assistant to javafx.fxml;
    exports com.thelibrary.controller.Librarian;
    opens com.thelibrary.controller.Librarian to javafx.fxml;
}