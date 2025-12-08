package models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Buys {
    private IntegerProperty invoice;
    private StringProperty customerSsn;
    private StringProperty productId;

    public Buys(int invoice, String customerSsn, String productId) {
        this.invoice = new SimpleIntegerProperty(invoice);
        this.customerSsn = new SimpleStringProperty(customerSsn);
        this.productId = new SimpleStringProperty(productId);
    }

    public int getInvoice() {
        return invoice.get();
    }

    public void setInvoice(int invoice) {
        this.invoice.set(invoice);
    }

    public IntegerProperty invoiceProperty() {
        return invoice;
    }

    public String getCustomerSsn() {
        return customerSsn.get();
    }

    public void setCustomerSsn(String customerSsn) {
        this.customerSsn.set(customerSsn);
    }

    public StringProperty customerSsnProperty() {
        return customerSsn;
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

    @Override
    public String toString() {
        return "Invoice: " + getInvoice() + " | Customer SSN: " + getCustomerSsn() +
                " | Product ID: " + getProductId();
    }
}