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
    requires javafx.swing;
    requires dom4j;

    requires org.slf4j;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.slf4j;
    requires org.apache.logging.log4j.core;

    requires ini4j;
    requires org.mariadb.jdbc;
    requires commons.math3;
    requires lucene.core;
    requires org.yaml.snakeyaml;
    requires org.reflections;
    requires org.jetbrains.annotations;
    requires libphonenumber;

    opens fr.sfc;
    opens fr.sfc.container;
    opens fr.sfc.repository;
    opens fr.sfc.entity;
    opens fr.sfc.common;
    opens fr.sfc.container.productTour;
    opens fr.sfc.controller.productTour;

    opens fr.sfc.framework;
    opens fr.sfc.framework.controlling;
    opens fr.sfc.framework.persistence;
    opens fr.sfc.framework.persistence.exception;
    opens fr.sfc.framework.controlling.annotation;
    opens fr.sfc.container.admin;
    opens fr.sfc.controller.admin;
    opens fr.sfc.container.common;
    opens fr.sfc.framework.item;
    opens fr.sfc.framework.injection;

}