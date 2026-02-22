package controllers;

import Exceptions.ManagerStillAssignedException;
import database.ManagerDAO;
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
import models.Manager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Label successLabel;
    @FXML
    private Label searchLabel;

    // TableView and TableColumns
    @FXML
    private TableView<Manager> managerTableView;
    @FXML
    private TableColumn<Manager, String> idColumn;
    @FXML
    private TableColumn<Manager, String> nameColumn;
    @FXML
    private TableColumn<Manager, String> phoneColumn;

    private String managerId;
    private String managerName;
    private String managerPhone;

    public void backToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToManager(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MANAGER/MANAGER.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addManager(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MANAGER/AddManager.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void removeManager(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MANAGER/RemoveManager.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateManager(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MANAGER/UpdateManager.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayManagers(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MANAGER/DisplayManagers.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void searchManager(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MANAGER/SearchManager.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void add(ActionEvent event){
        try {
            managerId = idTextField.getText().trim();
            managerName = nameTextField.getText().trim();
            managerPhone = phoneTextField.getText().trim();
            int Interger = Integer.parseInt(managerPhone);
            if(managerId.isEmpty() || managerName.isEmpty() || managerPhone.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please fill all text fields!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all text fields!");
                return;
            }
            if (ManagerDAO.addManager(managerId, managerName, managerPhone)) {
                MainController.showSuccess("Successfully added manager!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Manager added successfully!");
            }
        }catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Phone Number must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Phone Number!");
        } catch (SQLException e){
            if(e.getErrorCode() == 1062){
                MainController.showError("Error", "Addition Failed ",e.getMessage() );
                successLabel.setTextFill(Color.RED);
                successLabel.setText(e.getMessage());
            }
        } catch (Exception e) {
            MainController.showError("Error", "Operation Failed ",e.getMessage() );
            successLabel.setTextFill(Color.RED);
            successLabel.setText(e.getMessage());
        }
    }

    public void remove(ActionEvent event){
        try {
            managerId = idTextField.getText().trim();
            if(managerId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Manager ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Manager ID!");
                return;
            }
            if(ManagerDAO.deleteManager(managerId)){
                MainController.showSuccess("Manager deleted successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Manager deleted successfully!");
                refreshTable();
            }
        }catch (ManagerStillAssignedException e){
            MainController.showError("Error","Deletion Failed",e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText(e.getMessage());
        }
        catch (Exception e){
            successLabel.setText(e.getMessage());
        }
    }

    public void update(ActionEvent event){
        try {
            managerId = idTextField.getText().trim();
            managerName = nameTextField.getText().trim();
            managerPhone = phoneTextField.getText().trim();
            int  Interger = Integer.parseInt(managerPhone);
            if(managerId.isEmpty() || managerName.isEmpty() || managerPhone.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please fill all text fields!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all text fields!");
                return;
            }
            if (ManagerDAO.updateManager(managerId, managerName, managerPhone)) {
                MainController.showSuccess("Manager updated successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Manager updated successfully!");
                refreshTable();
            }
        }catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Phone Number must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Phone Number!");
        }catch (SQLException e){
            MainController.showError("Error","Update Failed",e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    public void search(ActionEvent event){
        try {
            managerId = idTextField.getText().trim();
            if(managerId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Manager ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Manager ID!");
                return;
            }
            Manager manager = ManagerDAO.searchManager(managerId);
            if(manager != null) {
                MainController.showSuccess("Manager found successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Manager found successfully!");
                searchLabel.setText(manager.toString());
                searchLabel.setTextFill(Color.GREEN);
            }
        }catch (SQLException e){
            MainController.showError("Error","Search Failed",e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (managerTableView != null) {
            setupTableColumns();
            loadManagersToTable();
            setupTableClickListener();
        }
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    private void loadManagersToTable(){
        List<Manager> managerList = ManagerDAO.getAllManagers();
        ObservableList<Manager> observableList = FXCollections.observableArrayList(managerList);
        if (managerTableView != null) {
            managerTableView.setItems(observableList);
        }
    }

    private void setupTableClickListener() {
        managerTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (idTextField != null) {
                    idTextField.setText(newSelection.getManagerId());
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
        loadManagersToTable();
        if (successLabel != null) {
            successLabel.setText("Table refreshed!");
            successLabel.setTextFill(Color.GREEN);
        }
    }
}