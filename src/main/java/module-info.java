module org.beni.gescartebanque {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires  org.hibernate.orm.core;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires static lombok;
    requires org.apache.logging.log4j;
    requires jdk.compiler;
    //requires eu.hansolo.tilesfx;
    opens org.beni.gescartebanque.entities to org.hibernate.orm.core;
    opens org.beni.gescartebanque to javafx.fxml;
    opens org.beni.gescartebanque.controllers to javafx.fxml;
    exports org.beni.gescartebanque;

}