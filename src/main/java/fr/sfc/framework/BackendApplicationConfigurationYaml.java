package fr.sfc.framework;

import java.util.List;

public final class BackendApplicationConfigurationYaml {

    public Config config;

    public BackendApplicationConfigurationYaml() { }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public static class Config {

        public String root;
        public Databases database;
        public Persistence persistence;

        public Config() { }

        public String getRoot() {
            return root;
        }

        public void setRoot(String root) {
            this.root = root;
        }

        public Databases getDatabase() {
            return database;
        }

        public void setDatabase(Databases database) {
            this.database = database;
        }

        public Persistence getPersistence() {
            return persistence;
        }

        public void setPersistence(Persistence persistence) {
            this.persistence = persistence;
        }

    }

    public static class Package {
        public String entity;
        public String repository;

        public Package() { }

        public String getEntity() {
            return entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

        public String getRepository() {
            return repository;
        }

        public void setRepository(String repository) {
            this.repository = repository;
        }
    }

    public static class Databases {
        public String properties;
        public List<String> databases;
        public String actual;

        public Databases() { }

        public String getProperties() {
            return properties;
        }

        public void setProperties(String properties) {
            this.properties = properties;
        }

        public List<String> getDatabases() {
            return databases;
        }

        public void setDatabases(List<String> databases) {
            this.databases = databases;
        }

        public String getActual() {
            return actual;
        }

        public void setActual(String actual) {
            this.actual = actual;
        }
    }

    public static class Persistence {

        public Package packages;
        public List<String> entities;
        public List<String> repositories;
        public List<String> others;

        public Persistence() { }

        public Package getPackages() {
            return packages;
        }

        public void setPackages(Package packages) {
            this.packages = packages;
        }

        public List<String> getEntities() {
            return entities;
        }

        public void setEntities(List<String> entities) {
            this.entities = entities;
        }

        public List<String> getRepositories() {
            return repositories;
        }

        public void setRepositories(List<String> repositories) {
            this.repositories = repositories;
        }

        public List<String> getOthers() {
            return others;
        }

        public void setOthers(List<String> others) {
            this.others = others;
        }
    }


}
