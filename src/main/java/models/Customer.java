package models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private StringProperty ssn;
    private StringProperty phone;
    private StringProperty name;

    public Customer(String ssn, String phone, String name) {
        this.ssn = new SimpleStringProperty(ssn);
        this.phone = new SimpleStringProperty(phone);
        this.name = new SimpleStringProperty(name);
    }

    public String getSsn() {
        return ssn.get();
    }
    public void setSsn(String ssn) {
        this.ssn.set(ssn);
    }
    public StringProperty ssnProperty() {
        return ssn;
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
        return "SSN: " + getSsn() + " | Name: " + getName() + " | Phone: " + getPhone();
    }
}