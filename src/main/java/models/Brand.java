package models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Brand {
    private StringProperty brandId;
    private StringProperty name;

    public Brand(String brandId, String name) {
        this.brandId = new SimpleStringProperty(brandId);
        this.name = new SimpleStringProperty(name);
    }

    public String getBrandId() {
        return brandId.get();
    }
    public void setBrandId(String brandId) {
        this.brandId.set(brandId);
    }
    public StringProperty brandIdProperty() {
        return brandId;
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
        return "ID: " + getBrandId() + " | Name: " + getName();
    }
}