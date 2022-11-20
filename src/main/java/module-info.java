module fr.sfc {

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

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    requires mysql.connector.j;
    requires lucene.core;
    requires ini4j;

    opens fr.sfc;
    opens fr.sfc.controller;
    opens fr.sfc.component to javafx.fxml;
}