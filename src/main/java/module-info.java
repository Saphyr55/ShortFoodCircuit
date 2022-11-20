module fr.sfc {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    requires java.xml;
	requires java.sql;
    requires java.base;
    requires java.compiler;
    requires java.desktop;
    requires java.datatransfer;
    requires java.net.http;
    requires java.management;
    requires java.se;
    requires java.security.jgss;
    requires java.security.sasl;
    requires mysql.connector.j;

    opens fr.sfc;
    opens fr.sfc.controllers;
    opens fr.sfc.components to javafx.fxml;
}