

import Exceptions.BrandStillUsedException;
import database.BrandDAO;
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
import models.Brand;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BrandController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label successLabel;
    @FXML
    private Label searchLabel;

    @FXML
    private TableView<Brand> brandTableView;
    @FXML
    private TableColumn<Brand, String> idColumn;
    @FXML
    private TableColumn<Brand, String> nameColumn;

    private String brandId;
    private String brandName;

    public void backToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToBrand(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRAND/BRAND.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addBrand(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRAND/AddBrand.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void removeBrand(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRAND/RemoveBrand.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateBrand(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRAND/UpdateBrand.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayBrands(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRAND/DisplayBrands.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void searchBrand(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRAND/SearchBrand.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void add(ActionEvent event) {
        try {
            brandId = idTextField.getText().trim();
            brandName = nameTextField.getText().trim();

            if(brandId.isEmpty() || brandName.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please fill all text fields!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all text fields!");
                return;
            }

            if (BrandDAO.addBrand(brandId, brandName)) {
                MainController.showSuccess("Successfully added brand!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Brand added successfully!");
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
            brandId = idTextField.getText().trim();

            if(brandId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Brand ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Brand ID!");
                return;
            }

            if(BrandDAO.deleteBrand(brandId)) {
                MainController.showSuccess("Brand deleted successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Brand deleted successfully!");
                refreshTable();
            }
        } catch (BrandStillUsedException e) {
            MainController.showError("Error", "Deletion Failed", e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText(e.getMessage());
        } catch (Exception e) {
            MainController.showError("Error", "Operation Failed", e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText(e.getMessage());
        }
    }

    public void update(ActionEvent event) {
        try {
            brandId = idTextField.getText().trim();
            brandName = nameTextField.getText().trim();

            if(brandId.isEmpty() || brandName.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please fill all text fields!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all text fields!");
                return;
            }

            if (BrandDAO.updateBrand(brandId, brandName)) {
                MainController.showSuccess("Brand updated successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Brand updated successfully!");
                refreshTable();
            }
        } catch (SQLException e) {
            MainController.showError("Error", "Update Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        } catch (Exception e) {
            MainController.showError("Error", "Operation Failed", e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText(e.getMessage());
        }
    }

    public void search(ActionEvent event) {
        try {
            brandId = idTextField.getText().trim();

            if(brandId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Brand ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Brand ID!");
                return;
            }

            Brand brand = BrandDAO.searchBrand(brandId);
            if(brand != null) {
                MainController.showSuccess("Brand found successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Brand found successfully!");
                searchLabel.setText(brand.toString());
                searchLabel.setTextFill(Color.GREEN);
            }
        } catch (SQLException e) {
            MainController.showError("Error", "Search Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        } catch (Exception e) {
            MainController.showError("Error", "Operation Failed", e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (brandTableView != null) {
            setupTableColumns();
            loadBrandsToTable();
            setupTableClickListener();
        }
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("brandId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadBrandsToTable() {
        List<Brand> brandList = BrandDAO.getAllBrands();
        ObservableList<Brand> observableList = FXCollections.observableArrayList(brandList);
        if (brandTableView != null) {
            brandTableView.setItems(observableList);
        }
    }

    private void setupTableClickListener() {
        brandTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (idTextField != null) {
                    idTextField.setText(newSelection.getBrandId());
                }
                if (nameTextField != null) {
                    nameTextField.setText(newSelection.getName());
                }
            }
        });
    }

    @FXML
    public void refreshTable() {
        loadBrandsToTable();
        if (successLabel != null) {
            successLabel.setText("Table refreshed!");
            successLabel.setTextFill(Color.GREEN);
        }
    }
}