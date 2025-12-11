package controllers;

import Exceptions.CustomerHasPurchasesException;
import database.CustomerDAO;
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
import models.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button button;
    @FXML
    private TextField ssnTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Label successLabel;
    @FXML
    private Label searchLabel;
    @FXML
    private ListView<Customer> customerListView;

    private String customerSsn;
    private String customerName;
    private String customerPhone;

    public void backToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/CUSTOMER.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/AddCustomer.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void removeCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/RemoveCustomer.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/UpdateCustomer.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayCustomers(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/DisplayCustomers.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void searchCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/SearchCustomer.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void add(ActionEvent event) {
        try {
            customerSsn = ssnTextField.getText().trim();
            customerName = nameTextField.getText().trim();
            customerPhone = phoneTextField.getText().trim();
            int interger = Integer.parseInt(customerPhone);
            if (customerSsn.isEmpty() || customerName.isEmpty() || customerPhone.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please fill all text fields!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all text fields!");
                return;
            }
            if (CustomerDAO.addCustomer(customerSsn, customerPhone, customerName)) {
                MainController.showSuccess("Successfully added customer!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Customer added successfully!");
            }
        }catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Phone Number must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Phone Number!");
        }catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                MainController.showError("Error", "Addition Failed", "Failed to add customer because already exists! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Customer already exists!");
            }
        } catch (Exception e) {
            successLabel.setText(e.getMessage());
        }
    }

    public void remove(ActionEvent event) {
        try {
            customerSsn = ssnTextField.getText().trim();
            if (customerSsn.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Customer SSN!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Customer SSN!");
                return;
            }
            if (CustomerDAO.deleteCustomer(customerSsn)) {
                MainController.showSuccess("Customer deleted successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Customer deleted successfully!");
                refreshList();
            } else {
                MainController.showError("Error", "Deletion Failed", "Failed to delete customer because customer not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Customer not found!");
            }
        } catch (CustomerHasPurchasesException e) {
            MainController.showError("Error", "Deletion Failed", e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Customer still has purchases!");
        } catch (Exception e) {
            successLabel.setText(e.getMessage());
        }
    }

    public void update(ActionEvent event) {
        try {
            customerSsn = ssnTextField.getText().trim();
            customerName = nameTextField.getText().trim();
            customerPhone = phoneTextField.getText().trim();
            int interger = Integer.parseInt(customerPhone);
            if (customerSsn.isEmpty() || customerName.isEmpty() || customerPhone.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please fill all text fields!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all text fields!");
                return;
            }
            if (CustomerDAO.updateCustomer(customerSsn, customerPhone, customerName)) {
                MainController.showSuccess("Customer updated successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Customer updated successfully!");
                refreshList();
            } else {
                MainController.showError("Error", "Update Failed", "Failed to update customer because customer not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Customer not found!");
            }
        } catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Phone Number must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Phone Number!");
        }catch (SQLException e) {
            MainController.showError("Error", "Update Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    public void search(ActionEvent event) {
        try {
            customerSsn = ssnTextField.getText().trim();
            if (customerSsn.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Customer SSN!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Customer SSN!");
                return;
            }
            Customer customer = CustomerDAO.searchCustomer(customerSsn);
            if (customer != null) {
                MainController.showSuccess("Customer found successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Customer found successfully!");
                searchLabel.setText(customer.toString());
                searchLabel.setTextFill(Color.GREEN);
            } else {
                MainController.showError("Error", "Search Failed", "Failed to find customer because customer not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Customer not found!");
            }
        } catch (SQLException e) {
            MainController.showError("Error", "Search Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (customerListView != null) {
            loadCustomersToList();
        }
    }

    private void loadCustomersToList() {
        List<Customer> customerList = CustomerDAO.getAllCustomers();
        ObservableList<Customer> observableList = FXCollections.observableArrayList(customerList);
        if (customerListView != null) {
            customerListView.setItems(observableList);
        }
    }

    public void refreshList() {
        loadCustomersToList();
        if (successLabel != null) {
            successLabel.setText("List refreshed!");
            successLabel.setTextFill(Color.GREEN);
        }
    }
}