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

    requires org.reflections;

    requires com.google.auto.service;
    requires com.google.protobuf;
    requires com.google.common;
    requires com.google.errorprone.annotations;

    opens fr.sfc;
    opens fr.sfc.controller;
    opens fr.sfc.api.core;
    opens fr.sfc.model.repository;
    opens fr.sfc.model.entity;
    opens fr.sfc.component;
    opens fr.sfc.api.resource;
    opens fr.sfc.api.core.expection;
    opens fr.sfc.api.component;
    opens fr.sfc.api.controller;
    opens fr.sfc.api;
}