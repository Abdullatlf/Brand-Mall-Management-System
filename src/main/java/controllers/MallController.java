package controllers;

import Exceptions.MallHasStoresException;
import database.MallDAO;
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
import models.Mall;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MallController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button button;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private Label successLabel;
    @FXML
    private Label searchLabel;
    @FXML
    private ListView<Mall> mallListView;

    private String mallId;
    private String mallName;
    private String mallLocation;

    public void backToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToMall(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/MALL.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addMall(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/AddMall.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void removeMall(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/RemoveMall.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateMall(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/UpdateMall.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayMalls(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/DisplayMalls.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void searchMall(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/SearchMall.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void add(ActionEvent event) {
        try {
            mallId = idTextField.getText().trim();
            mallName = nameTextField.getText().trim();
            mallLocation = locationTextField.getText().trim();
            if (mallId.isEmpty() || mallName.isEmpty() || mallLocation.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please fill all text fields!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all text fields!");
                return;
            }
            if (MallDAO.addMall(mallId, mallLocation, mallName)) {
                MainController.showSuccess("Successfully added mall!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Mall added successfully!");
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                MainController.showError("Error", "Addition Failed", "Failed to add mall because already exists! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Mall already exists!");
            }
        } catch (Exception e) {
            successLabel.setText(e.getMessage());
        }
    }

    public void remove(ActionEvent event) {
        try {
            mallId = idTextField.getText().trim();
            if (mallId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Mall ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Mall ID!");
                return;
            }
            if (MallDAO.deleteMall(mallId)) {
                MainController.showSuccess("Mall deleted successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Mall deleted successfully!");
                refreshList();
            } else {
                MainController.showError("Error", "Deletion Failed", "Failed to delete mall because mall not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Mall not found!");
            }
        } catch (MallHasStoresException e) {
            MainController.showError("Error", "Deletion Failed", e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Mall still has stores!");
        } catch (Exception e) {
            successLabel.setText(e.getMessage());
        }
    }

    public void update(ActionEvent event) {
        try {
            mallId = idTextField.getText().trim();
            mallName = nameTextField.getText().trim();
            mallLocation = locationTextField.getText().trim();
            if (mallId.isEmpty() || mallName.isEmpty() || mallLocation.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please fill all text fields!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all text fields!");
                return;
            }
            if (MallDAO.updateMall(mallId, mallLocation, mallName)) {
                MainController.showSuccess("Mall updated successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Mall updated successfully!");
                refreshList();
            } else {
                MainController.showError("Error", "Update Failed", "Failed to update mall because mall not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Mall not found!");
            }
        } catch (SQLException e) {
            MainController.showError("Error", "Update Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    public void search(ActionEvent event) {
        try {
            mallId = idTextField.getText().trim();
            if (mallId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Mall ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Mall ID!");
                return;
            }
            Mall mall = MallDAO.searchMall(mallId);
            if (mall != null) {
                MainController.showSuccess("Mall found successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Mall found successfully!");
                searchLabel.setText(mall.toString());
                searchLabel.setTextFill(Color.GREEN);
            } else {
                MainController.showError("Error", "Search Failed", "Failed to find mall because mall not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Mall not found!");
            }
        } catch (SQLException e) {
            MainController.showError("Error", "Search Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (mallListView != null) {
            loadMallsToList();
        }
    }

    private void loadMallsToList() {
        List<Mall> mallList = MallDAO.getAllMalls();
        ObservableList<Mall> observableList = FXCollections.observableArrayList(mallList);
        if (mallListView != null) {
            mallListView.setItems(observableList);
        }
    }

    public void refreshList() {
        loadMallsToList();
        if (successLabel != null) {
            successLabel.setText("List refreshed!");
            successLabel.setTextFill(Color.GREEN);
        }
    }
}