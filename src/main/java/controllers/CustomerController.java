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
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> ssnColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;

    private String customerSsn;
    private String customerName;
    private String customerPhone;

    public void backToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/CUSTOMER.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/AddCustomer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void removeCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/RemoveCustomer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/UpdateCustomer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayCustomers(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/DisplayCustomers.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void searchCustomer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/SearchCustomer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void add(ActionEvent event) {
        try {
            customerSsn = ssnTextField.getText().trim();
            customerName = nameTextField.getText().trim();
            customerPhone = phoneTextField.getText().trim();
            int integer = Integer.parseInt(customerPhone);
            if(customerSsn.isEmpty() || customerName.isEmpty() || customerPhone.isEmpty()) {
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
        } catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Phone Number must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Phone Number!");
        } catch (SQLException e) {
            if(e.getErrorCode() == 1062) {
                MainController.showError("Error", "Addition Failed", e.getMessage());
                successLabel.setTextFill(Color.RED);
                successLabel.setText(e.getMessage());
            }
        } catch (Exception e) {
            MainController.showError("Error", "Operation Failed", e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText(e.getMessage());
        }
    }

    public void remove(ActionEvent event) {
        try {
            customerSsn = ssnTextField.getText().trim();
            if(customerSsn.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Customer SSN!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Customer SSN!");
                return;
            }
            if(CustomerDAO.deleteCustomer(customerSsn)) {
                MainController.showSuccess("Customer deleted successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Customer deleted successfully!");
                refreshTable();
            }
        } catch (CustomerHasPurchasesException e) {
            MainController.showError("Error", "Deletion Failed", e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText(e.getMessage());
        } catch (Exception e) {
            successLabel.setText(e.getMessage());
        }
    }

    public void update(ActionEvent event) {
        try {
            customerSsn = ssnTextField.getText().trim();
            customerName = nameTextField.getText().trim();
            customerPhone = phoneTextField.getText().trim();
            int integer = Integer.parseInt(customerPhone);
            if(customerSsn.isEmpty() || customerName.isEmpty() || customerPhone.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please fill all text fields!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all text fields!");
                return;
            }
            if (CustomerDAO.updateCustomer(customerSsn, customerPhone, customerName)) {
                MainController.showSuccess("Customer updated successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Customer updated successfully!");
                refreshTable();
            }
        } catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Phone Number must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Phone Number!");
        } catch (SQLException e) {
            MainController.showError("Error", "Update Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    public void search(ActionEvent event) {
        try {
            customerSsn = ssnTextField.getText().trim();
            if(customerSsn.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Customer SSN!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Customer SSN!");
                return;
            }
            Customer customer = CustomerDAO.searchCustomer(customerSsn);
            if(customer != null) {
                MainController.showSuccess("Customer found successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Customer found successfully!");
                searchLabel.setText(customer.toString());
                searchLabel.setTextFill(Color.GREEN);
            }
        } catch (SQLException e) {
            MainController.showError("Error", "Search Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (customerTableView != null) {
            setupTableColumns();
            loadCustomersToTable();
            setupTableClickListener();
        }
    }

    private void setupTableColumns() {
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    private void loadCustomersToTable() {
        List<Customer> customerList = CustomerDAO.getAllCustomers();
        ObservableList<Customer> observableList = FXCollections.observableArrayList(customerList);
        if (customerTableView != null) {
            customerTableView.setItems(observableList);
        }
    }

    private void setupTableClickListener() {
        customerTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (ssnTextField != null) {
                    ssnTextField.setText(newSelection.getSsn());
                }
                if (nameTextField != null) {
                    nameTextField.setText(newSelection.getName());
                }
                if (phoneTextField != null) {
                    phoneTextField.setText(newSelection.getPhone());
                }
            }
        });
    }

    @FXML
    public void refreshTable() {
        loadCustomersToTable();
        if (successLabel != null) {
            successLabel.setText("Table refreshed!");
            successLabel.setTextFill(Color.GREEN);
        }
    }
}