package controllers;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BuysController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button button;
    @FXML
    private TextField invoiceTextField;
    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<Product> productComboBox;
    @FXML
    private Label successLabel;
    @FXML
    private Label searchLabel;
    @FXML
    private ListView<Buys> buysListView;

    public void backToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToBuys(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BUYS/BUYS.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addBuys(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BUYS/AddBuys.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void removeBuys(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BUYS/RemoveBuys.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateBuys(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BUYS/UpdateBuys.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayBuys(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BUYS/DisplayBuys.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void searchBuys(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BUYS/SearchBuys.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void add(ActionEvent event) {
        try {
            String invoiceStr = invoiceTextField.getText().trim();
            Customer selectedCustomer = customerComboBox.getValue();
            Product selectedProduct = productComboBox.getValue();

            if (invoiceStr.isEmpty() || selectedCustomer == null || selectedProduct == null) {
                MainController.showError("Error", "Empty Field", "Please fill all fields and select from dropdowns!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all fields!");
                return;
            }

            int invoice = Integer.parseInt(invoiceStr);
            if (BuysDAO.addBuys(invoice, selectedCustomer.getSsn(), selectedProduct.getProductId())) {
                MainController.showSuccess("Successfully added purchase record!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Purchase added successfully!");
            }
        } catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Invoice must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Invoice Number!");
        } catch (SQLException e) {
            MainController.showError("Error", "Addition Failed", e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Addition failed!");
        } catch (Exception e) {
            successLabel.setText(e.getMessage());
        }
    }

    public void remove(ActionEvent event) {
        try {
            String invoiceStr = invoiceTextField.getText().trim();

            if (invoiceStr.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter an Invoice number!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Invoice number!");
                return;
            }

            int invoice = Integer.parseInt(invoiceStr);
            if (BuysDAO.deleteBuys(invoice)) {
                MainController.showSuccess("Purchase record deleted successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Purchase deleted successfully!");
                refreshList();
            } else {
                MainController.showError("Error", "Deletion Failed", "Failed to delete purchase because record not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Purchase not found!");
            }
        } catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Invoice must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Invoice Number!");
        } catch (Exception e) {
            successLabel.setText(e.getMessage());
        }
    }

    public void update(ActionEvent event) {
        try {
            String invoiceStr = invoiceTextField.getText().trim();
            Customer selectedCustomer = customerComboBox.getValue();
            Product selectedProduct = productComboBox.getValue();

            if (invoiceStr.isEmpty() || selectedCustomer == null || selectedProduct == null) {
                MainController.showError("Error", "Empty Field", "Please fill all fields and select from dropdowns!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all fields!");
                return;
            }

            int invoice = Integer.parseInt(invoiceStr);
            if (BuysDAO.updateBuys(invoice, selectedCustomer.getSsn(), selectedProduct.getProductId())) {
                MainController.showSuccess("Purchase record updated successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Purchase updated successfully!");
                refreshList();
            } else {
                MainController.showError("Error", "Update Failed", "Failed to update purchase because record not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Purchase not found!");
            }
        } catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Invoice must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Invoice Number!");
        } catch (SQLException e) {
            MainController.showError("Error", "Update Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    public void search(ActionEvent event) {
        try {
            String invoiceStr = invoiceTextField.getText().trim();

            if (invoiceStr.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter an Invoice number!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Invoice number!");
                return;
            }

            int invoice = Integer.parseInt(invoiceStr);
            Buys buys = BuysDAO.searchBuys(invoice);
            if (buys != null) {
                MainController.showSuccess("Purchase record found successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Purchase found successfully!");
                searchLabel.setText(buys.toString());
                searchLabel.setTextFill(Color.GREEN);
            } else {
                MainController.showError("Error", "Search Failed", "Failed to find purchase because record not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Purchase not found!");
            }
        } catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Invoice must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Invoice Number!");
        } catch (SQLException e) {
            MainController.showError("Error", "Search Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (buysListView != null) {
            loadBuysToList();
        }
        if (customerComboBox != null) {
            loadCustomersToComboBox();
        }
        if (productComboBox != null) {
            loadProductsToComboBox();
        }
    }

    private void loadBuysToList() {
        List<Buys> buysList = BuysDAO.getAllBuys();
        ObservableList<Buys> observableList = FXCollections.observableArrayList(buysList);
        if (buysListView != null) {
            buysListView.setItems(observableList);
        }
    }

    private void loadCustomersToComboBox() {
        List<Customer> customerList = CustomerDAO.getAllCustomers();
        ObservableList<Customer> observableList = FXCollections.observableArrayList(customerList);
        customerComboBox.setItems(observableList);
        customerComboBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return customer != null ? customer.getName() + " - " + customer.getSsn() : "";
            }

            @Override
            public Customer fromString(String string) {
                return null;
            }
        });
    }

    private void loadProductsToComboBox() {
        List<Product> productList = ProductDAO.getAllProducts();
        ObservableList<Product> observableList = FXCollections.observableArrayList(productList);
        productComboBox.setItems(observableList);
        productComboBox.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product product) {
                return product != null ? product.getName() + " (" + product.getProductId() + ")" : "";
            }

            @Override
            public Product fromString(String string) {
                return null;
            }
        });
    }

    public void refreshList() {
        loadBuysToList();
        if (successLabel != null) {
            successLabel.setText("List refreshed!");
            successLabel.setTextFill(Color.GREEN);
        }
    }
}