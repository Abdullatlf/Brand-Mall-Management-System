package models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Mall {
    private StringProperty mallId;
    private StringProperty location;
    private StringProperty name;

    public Mall(String mallId, String location, String name) {
        this.mallId = new SimpleStringProperty(mallId);
        this.location = new SimpleStringProperty(location);
        this.name = new SimpleStringProperty(name);
    }

    public String getMallId() {
        return mallId.get();
    }

    public void setMallId(String mallId) {
        this.mallId.set(mallId);
    }

    public StringProperty mallIdProperty() {
        return mallId;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return "ID: " + getMallId() + " | Name: " + getName() + " | Location: " + getLocation();
    }
}