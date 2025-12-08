package models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BrandStore {
    private IntegerProperty storeNo;
    private StringProperty address;
    private StringProperty brandId;
    private StringProperty managerId;
    private StringProperty mallId;

    public BrandStore(int storeNo, String address, String brandId, String managerId, String mallId) {
        this.storeNo = new SimpleIntegerProperty(storeNo);
        this.address = new SimpleStringProperty(address);
        this.brandId = new SimpleStringProperty(brandId);
        this.managerId = new SimpleStringProperty(managerId);
        this.mallId = new SimpleStringProperty(mallId);
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

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
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

    public String getManagerId() {
        return managerId.get();
    }

    public void setManagerId(String managerId) {
        this.managerId.set(managerId);
    }

    public StringProperty managerIdProperty() {
        return managerId;
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

    @Override
    public String toString() {
        return "Store#: " + getStoreNo() + " | Brand: " + getBrandId() + " | Address: " + getAddress() +
                " | Manager: " + getManagerId() + " | Mall: " + getMallId();
    }
}