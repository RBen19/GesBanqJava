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
    requires  org.postgresql.jdbc;
    requires static lombok;
 //   requires org.apache.logging.log4j;
    requires jdk.compiler;
    requires ch.qos.logback.classic;
    requires jakarta.mail;
    requires org.slf4j;
    //requires eu.hansolo.tilesfx;
    opens org.beni.gescartebanque.entities to org.hibernate.orm.core,javafx.base;
    opens org.beni.gescartebanque to javafx.fxml;
    opens org.beni.gescartebanque.controllers to javafx.fxml,javafx.base;
    opens org.beni.gescartebanque.services to javafx.base;
    opens org.beni.gescartebanque.interfaces to javafx.fxml;
    exports org.beni.gescartebanque;
    exports org.beni.gescartebanque.entities;

}