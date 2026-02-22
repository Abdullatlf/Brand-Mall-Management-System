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
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TextField mallIdTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private Label successLabel;
    @FXML
    private Label searchLabel;

    @FXML
    private TableView<Mall> mallTableView;
    @FXML
    private TableColumn<Mall, String> mallIdColumn;
    @FXML
    private TableColumn<Mall, String> nameColumn;
    @FXML
    private TableColumn<Mall, String> locationColumn;

    private String mallId;
    private String mallName;
    private String mallLocation;

    public void backToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToMall(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/MALL.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addMall(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/AddMall.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void removeMall(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/RemoveMall.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateMall(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/UpdateMall.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayMalls(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/DisplayMalls.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void searchMall(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/SearchMall.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void add(ActionEvent event) {
        try {
            mallId = mallIdTextField.getText().trim();
            mallName = nameTextField.getText().trim();
            mallLocation = locationTextField.getText().trim();
            if(mallId.isEmpty() || mallName.isEmpty() || mallLocation.isEmpty()) {
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
            mallId = mallIdTextField.getText().trim();
            if(mallId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Mall ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Mall ID!");
                return;
            }
            if(MallDAO.deleteMall(mallId)) {
                MainController.showSuccess("Mall deleted successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Mall deleted successfully!");
                refreshTable();
            }
        } catch (MallHasStoresException e) {
            MainController.showError("Error", "Deletion Failed", e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText(e.getMessage());
        } catch (Exception e) {
            successLabel.setText(e.getMessage());
        }
    }

    public void update(ActionEvent event) {
        try {
            mallId = mallIdTextField.getText().trim();
            mallName = nameTextField.getText().trim();
            mallLocation = locationTextField.getText().trim();
            if(mallId.isEmpty() || mallName.isEmpty() || mallLocation.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please fill all text fields!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all text fields!");
                return;
            }
            if (MallDAO.updateMall(mallId, mallLocation, mallName)) {
                MainController.showSuccess("Mall updated successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Mall updated successfully!");
                refreshTable();
            }
        } catch (SQLException e) {
            MainController.showError("Error", "Update Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    public void search(ActionEvent event) {
        try {
            mallId = mallIdTextField.getText().trim();
            if(mallId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Mall ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Mall ID!");
                return;
            }
            Mall mall = MallDAO.searchMall(mallId);
            if(mall != null) {
                MainController.showSuccess("Mall found successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Mall found successfully!");
                searchLabel.setText(mall.toString());
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
        if (mallTableView != null) {
            setupTableColumns();
            loadMallsToTable();
            setupTableClickListener();
        }
    }

    private void setupTableColumns() {
        mallIdColumn.setCellValueFactory(new PropertyValueFactory<>("mallId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    private void loadMallsToTable() {
        List<Mall> mallList = MallDAO.getAllMalls();
        ObservableList<Mall> observableList = FXCollections.observableArrayList(mallList);
        if (mallTableView != null) {
            mallTableView.setItems(observableList);
        }
    }

    private void setupTableClickListener() {
        mallTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (mallIdTextField != null) {
                    mallIdTextField.setText(newSelection.getMallId());
                }
                if (nameTextField != null) {
                    nameTextField.setText(newSelection.getName());
                }
                if (locationTextField != null) {
                    locationTextField.setText(newSelection.getLocation());
                }
            }
        });
    }

    @FXML
    public void refreshTable() {
        loadMallsToTable();
        if (successLabel != null) {
            successLabel.setText("Table refreshed!");
            successLabel.setTextFill(Color.GREEN);
        }
    }
}