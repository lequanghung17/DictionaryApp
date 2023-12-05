module com.example.DICTIONARY {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires freetts;

    opens com.example.dictionary.base;
    exports com.example.dictionary.base to javafx.graphics;
    
    opens com.example.dictionary.Controller;
    exports com.example.dictionary.Controller to javafx.fxml;
    exports com.example.dictionary;
    opens com.example.dictionary to javafx.fxml;
}