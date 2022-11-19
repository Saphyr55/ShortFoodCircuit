module fr.sfc {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

	requires java.sql;
    requires mysql.connector.j;
    requires lucene.core;

    opens fr.sfc;
    opens fr.sfc.controllers;

}