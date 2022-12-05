module fr.sfc {

    requires java.se;

    requires jdk.accessibility;
    requires jdk.attach;
    requires jdk.jsobject;
    requires jdk.httpserver;
    requires jdk.net;
    requires jdk.sctp;
    requires jdk.charsets;
    requires jdk.compiler;
    requires jdk.javadoc;
    requires jdk.xml.dom;
    requires jdk.random;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    requires org.jetbrains.annotations;

    requires mariadb.java.client;
    requires commons.math3;

    requires lucene.core;

    requires org.yaml.snakeyaml;

    requires ini4j;

    requires com.google.common;

    requires org.reflections;

    opens fr.sfc;
    opens fr.sfc.controller;
    opens fr.sfc.component;
    opens fr.sfc.repository;
    opens fr.sfc.entity;

    opens fr.sfc.api;
    opens fr.sfc.api.controlling;
    opens fr.sfc.api.persistence;
    opens fr.sfc.api.persistence.exception;
    opens fr.sfc.common;
    opens fr.sfc.component.productTour;
    opens fr.sfc.controller.productTour;

}