package models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Product {
    private StringProperty productId;
    private StringProperty name;
    private IntegerProperty storeNo;
    private StringProperty brandId;

    public Product(String productId, String name, int storeNo, String brandId) {
        this.productId = new SimpleStringProperty(productId);
        this.name = new SimpleStringProperty(name);
        this.storeNo = new SimpleIntegerProperty(storeNo);
        this.brandId = new SimpleStringProperty(brandId);
    }

    public String getProductId() {
        return productId.get();
    }

    public void setProductId(String productId) {
        this.productId.set(productId);
    }

    public StringProperty productIdProperty() {
        return productId;
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

    public int getStoreNo() {
        return storeNo.get();
    }

    public void setStoreNo(int storeNo) {
        this.storeNo.set(storeNo);
    }

    public IntegerProperty storeNoProperty() {
        return storeNo;
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

    @Override
    public String toString() {
        return "ID: " + getProductId() + " | Name: " + getName() +
                " | Store#: " + getStoreNo() + " | Brand: " + getBrandId();
    }
}