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
    @FXML
    private ListView<Manager> managerListView;

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
        //System.out.println("addManager");
        root = FXMLLoader.load(getClass().getResource("/MANAGER/AddManager.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void removeManager(ActionEvent event) throws IOException {
        //System.out.println("removeManager");
        root = FXMLLoader.load(getClass().getResource("/MANAGER/RemoveManager.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void updateManager(ActionEvent event) throws IOException {
        //System.out.println("updateManager");
        root = FXMLLoader.load(getClass().getResource("/MANAGER/UpdateManager.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void displayManagers(ActionEvent event) throws IOException {
        //System.out.println("displayManagers");
        root = FXMLLoader.load(getClass().getResource("/MANAGER/DisplayManagers.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void searchManager(ActionEvent event) throws IOException {
        //System.out.println("searchManager");
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
                MainController.showError("Error", "addation faied","Failed to add manager because already exists!. Please try again." );
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Manager already exists!");
            }
        } catch (Exception e) {
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
                refreshList();
            }else {
                MainController.showError("Error","Deletion Failed","Failed to delete manager because manager not found!. Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Manager not found!");
            }
        }catch (ManagerStillAssignedException e){
            MainController.showError("Error","Deletion Failed",e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Manager still assigned to stores!");
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
                refreshList();
            }else {
                MainController.showError("Error","Update Failed","Failed to update manager because manager not found!. Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Manager not found!");
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
            }else{
                MainController.showError("Error","Search Failed","Failed to find manager because manager not found!. Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Manager not found!");
            }
        }catch (SQLException e){
            MainController.showError("Error","Search Failed",e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (managerListView != null) {
            loadManagersToList();
        }
    }
    private void loadManagersToList(){
        List<Manager> managerList = ManagerDAO.getAllManagers();
        ObservableList<Manager> observableList = FXCollections.observableArrayList(managerList);
        if (managerListView != null) {
            managerListView.setItems(observableList);
        }
    }
    @FXML
    public void refreshList() {
        loadManagersToList();
        if (successLabel != null) {
            successLabel.setText("List refreshed!");
            successLabel.setTextFill(Color.GREEN);
        }
    }
}
