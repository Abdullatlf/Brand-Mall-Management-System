package controllers;

import Exceptions.StoreHasProductsException;
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

public class BrandStoreController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button button;
    @FXML
    private TextField storeNoTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField brandIdTextField;
    @FXML
    private ComboBox<Brand> brandComboBox;
    @FXML
    private ComboBox<Manager> managerComboBox;
    @FXML
    private ComboBox<Mall> mallComboBox;
    @FXML
    private Label successLabel;
    @FXML
    private Label searchLabel;
    @FXML
    private ListView<BrandStore> storeListView;

    public void backToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToBrandStore(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRANDSTORE/BRANDSTORE.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addBrandStore(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRANDSTORE/AddBrandStore.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void removeBrandStore(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRANDSTORE/RemoveBrandStore.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateBrandStore(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRANDSTORE/UpdateBrandStore.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayBrandStores(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRANDSTORE/DisplayBrandStores.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void searchBrandStore(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRANDSTORE/SearchBrandStore.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void add(ActionEvent event) {
        try {
            String storeNoStr = storeNoTextField.getText().trim();
            String address = addressTextField.getText().trim();
            Brand selectedBrand = brandComboBox.getValue();
            Manager selectedManager = managerComboBox.getValue();
            Mall selectedMall = mallComboBox.getValue();

            if (storeNoStr.isEmpty() || address.isEmpty() || selectedBrand == null || selectedManager == null || selectedMall == null) {
                MainController.showError("Error", "Empty Field", "Please fill all fields and select from dropdowns!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all fields!");
                return;
            }

            int storeNo = Integer.parseInt(storeNoStr);
            if (BrandStoreDAO.addBrandStore(storeNo, address, selectedBrand.getBrandId(), selectedManager.getManagerId(), selectedMall.getMallId())) {
                MainController.showSuccess("Successfully added brand store!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Brand Store added successfully!");
            }
        } catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Store Number must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Store Number!");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                MainController.showError("Error", "Addition Failed", "Failed to add brand store because already exists! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Brand Store already exists!");
            } else {
                MainController.showError("Error", "Addition Failed", e.getMessage());
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Addition failed!");
            }
        } catch (Exception e) {
            successLabel.setText(e.getMessage());
        }
    }

    public void remove(ActionEvent event) {
        try {
            String storeNoStr = storeNoTextField.getText().trim();
            String brandId = brandIdTextField.getText().trim();

            if (storeNoStr.isEmpty() || brandId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter Store Number and Brand ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Store Number and Brand ID!");
                return;
            }

            int storeNo = Integer.parseInt(storeNoStr);
            if (BrandStoreDAO.deleteBrandStore(storeNo, brandId)) {
                MainController.showSuccess("Brand Store deleted successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Brand Store deleted successfully!");
                refreshList();
            } else {
                MainController.showError("Error", "Deletion Failed", "Failed to delete brand store because store not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Brand Store not found!");
            }
        } catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Store Number must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Store Number!");
        } catch (StoreHasProductsException e) {
            MainController.showError("Error", "Deletion Failed", e.getMessage());
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Store still has products!");
        } catch (Exception e) {
            successLabel.setText(e.getMessage());
        }
    }

    public void update(ActionEvent event) {
        try {
            String storeNoStr = storeNoTextField.getText().trim();
            String address = addressTextField.getText().trim();
            String brandId = brandIdTextField.getText().trim();
            Manager selectedManager = managerComboBox.getValue();
            Mall selectedMall = mallComboBox.getValue();

            if (storeNoStr.isEmpty() || address.isEmpty() || brandId.isEmpty() || selectedManager == null || selectedMall == null) {
                MainController.showError("Error", "Empty Field", "Please fill all fields and select from dropdowns!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all fields!");
                return;
            }

            int storeNo = Integer.parseInt(storeNoStr);
            if (BrandStoreDAO.updateBrandStore(storeNo, brandId, address, selectedManager.getManagerId(), selectedMall.getMallId())) {
                MainController.showSuccess("Brand Store updated successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Brand Store updated successfully!");
                refreshList();
            } else {
                MainController.showError("Error", "Update Failed", "Failed to update brand store because store not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Brand Store not found!");
            }
        } catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Store Number must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Store Number!");
        } catch (SQLException e) {
            MainController.showError("Error", "Update Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    public void search(ActionEvent event) {
        try {
            String storeNoStr = storeNoTextField.getText().trim();
            String brandId = brandIdTextField.getText().trim();

            if (storeNoStr.isEmpty() || brandId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter Store Number and Brand ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Store Number and Brand ID!");
                return;
            }

            int storeNo = Integer.parseInt(storeNoStr);
            BrandStore store = BrandStoreDAO.searchBrandStore(storeNo, brandId);
            if (store != null) {
                MainController.showSuccess("Brand Store found successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Brand Store found successfully!");
                searchLabel.setText(store.toString());
                searchLabel.setTextFill(Color.GREEN);
            } else {
                MainController.showError("Error", "Search Failed", "Failed to find brand store because store not found! Please try again.");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Brand Store not found!");
            }
        } catch (NumberFormatException e) {
            MainController.showError("Error", "Invalid Input", "Store Number must be a valid integer!");
            successLabel.setTextFill(Color.RED);
            successLabel.setText("Invalid Store Number!");
        } catch (SQLException e) {
            MainController.showError("Error", "Search Failed", e.getMessage());
            successLabel.setText(e.getMessage());
            successLabel.setTextFill(Color.RED);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (storeListView != null) {
            loadStoresToList();
        }
        if (brandComboBox != null) {
            loadBrandsToComboBox();
        }
        if (managerComboBox != null) {
            loadManagersToComboBox();
        }
        if (mallComboBox != null) {
            loadMallsToComboBox();
        }
    }

    private void loadStoresToList() {
        List<BrandStore> storeList = BrandStoreDAO.getAllBrandStores();
        ObservableList<BrandStore> observableList = FXCollections.observableArrayList(storeList);
        if (storeListView != null) {
            storeListView.setItems(observableList);
        }
    }

    private void loadBrandsToComboBox() {
        List<Brand> brandList = BrandDAO.getAllBrands();
        ObservableList<Brand> observableList = FXCollections.observableArrayList(brandList);
        brandComboBox.setItems(observableList);
        brandComboBox.setConverter(new StringConverter<Brand>() {
            @Override
            public String toString(Brand brand) {
                return brand != null ? brand.getName() + " (" + brand.getBrandId() + ")" : "";
            }

            @Override
            public Brand fromString(String string) {
                return null;
            }
        });
    }

    private void loadManagersToComboBox() {
        List<Manager> managerList = ManagerDAO.getAllManagers();
        ObservableList<Manager> observableList = FXCollections.observableArrayList(managerList);
        managerComboBox.setItems(observableList);
        managerComboBox.setConverter(new StringConverter<Manager>() {
            @Override
            public String toString(Manager manager) {
                return manager != null ? manager.getName() + " (" + manager.getManagerId() + ")" : "";
            }

            @Override
            public Manager fromString(String string) {
                return null;
            }
        });
    }

    private void loadMallsToComboBox() {
        List<Mall> mallList = MallDAO.getAllMalls();
        ObservableList<Mall> observableList = FXCollections.observableArrayList(mallList);
        mallComboBox.setItems(observableList);
        mallComboBox.setConverter(new StringConverter<Mall>() {
            @Override
            public String toString(Mall mall) {
                return mall != null ? mall.getName() + " (" + mall.getMallId() + ")" : "";
            }

            @Override
            public Mall fromString(String string) {
                return null;
            }
        });
    }

    public void refreshList() {
        loadStoresToList();
        if (successLabel != null) {
            successLabel.setText("List refreshed!");
            successLabel.setTextFill(Color.GREEN);
        }
    }
}