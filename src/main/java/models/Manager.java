package models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Manager {
    private StringProperty managerId;
    private StringProperty name;
    private StringProperty phone;

    public Manager(String managerId, String name, String phone) {
        this.managerId = new SimpleStringProperty(managerId);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
    }
    public String getManagerId() {
        return managerId.get();
    }
    public void setManagerId(String managerId) {
        this.managerId.set(managerId);
    }
    public StringProperty managerIdProperty() {
        return managerId;
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
    public String getPhone() {
        return phone.get();
    }
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    public StringProperty phoneProperty() {
        return phone;
    }

    public String toString() {
        return "ID:   " + getManagerId() + "  | Name:   " + getName() + "   | Phone:   " + getPhone();
    }

}
