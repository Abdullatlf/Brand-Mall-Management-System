package controllers;

import Exceptions.ProductInPurchasesException;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField productIdTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<Brand> brandComboBox;
    @FXML
    private ComboBox<BrandStore> storeComboBox;
    @FXML
    private Label successLabel;
    @FXML
    private Label searchLabel;

    // TableView and TableColumns
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, String> productIdColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Integer> storeNoColumn;
    @FXML
    private TableColumn<Product, String> brandIdColumn;

    public void backToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void backToProduct(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/PRODUCT/PRODUCT.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addProduct(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/PRODUCT/AddProduct.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void removeProduct(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/PRODUCT/RemoveProduct.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateProduct(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/PRODUCT/UpdateProduct.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayProducts(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/PRODUCT/DisplayProducts.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void searchProduct(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/PRODUCT/SearchProduct.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void add(ActionEvent event) {
        try {
            String productId = productIdTextField.getText().trim();
            String name = nameTextField.getText().trim();
            BrandStore selectedStore = storeComboBox.getValue();

            if (productId.isEmpty() || name.isEmpty() || selectedStore == null) {
                MainController.showError("Error", "Empty Field", "Please fill all fields and select a store!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all fields!");
                return;
            }

            if (ProductDAO.addProduct(productId, name, selectedStore.getStoreNo(), selectedStore.getBrandId())) {
                MainController.showSuccess("Successfully added product!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Product added successfully!");
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                MainController.showError("Error", "Addition Failed", e.getMessage());
                successLabel.setTextFill(Color.RED);
                successLabel.setText(e.getMessage());
            } else {
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
            String productId = productIdTextField.getText().trim();

            if (productId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Product ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Product ID!");
                return;
            }

            if (ProductDAO.deleteProduct(productId)) {
                MainController.showSuccess("Product deleted successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Product deleted successfully!");
                refreshTable();
            }
        } catch (ProductInPurchasesException e) {
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
            String productId = productIdTextField.getText().trim();
            String name = nameTextField.getText().trim();
            BrandStore selectedStore = storeComboBox.getValue();

            if (productId.isEmpty() || name.isEmpty() || selectedStore == null) {
                MainController.showError("Error", "Empty Field", "Please fill all fields and select a store!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please fill all fields!");
                return;
            }

            if (ProductDAO.updateProduct(productId, name, selectedStore.getStoreNo(), selectedStore.getBrandId())) {
                MainController.showSuccess("Product updated successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Product updated successfully!");
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
            String productId = productIdTextField.getText().trim();

            if (productId.isEmpty()) {
                MainController.showError("Error", "Empty Field", "Please enter a Product ID!");
                successLabel.setTextFill(Color.RED);
                successLabel.setText("Please enter Product ID!");
                return;
            }

            Product product = ProductDAO.searchProduct(productId);
            if (product != null) {
                MainController.showSuccess("Product found successfully!");
                successLabel.setTextFill(Color.GREEN);
                successLabel.setText("Product found successfully!");
                searchLabel.setText(product.toString());
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
        if (productTableView != null) {
            setupTableColumns();
            loadProductsToTable();
            setupTableClickListener();
        }
        if (brandComboBox != null) {
            loadBrandsToComboBox();
            brandComboBox.setOnAction(e -> filterStoresByBrand());
        }
        if (storeComboBox != null && brandComboBox == null) {
            loadAllStoresToComboBox();
        }
    }

    private void setupTableColumns() {
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        storeNoColumn.setCellValueFactory(new PropertyValueFactory<>("storeNo"));
        brandIdColumn.setCellValueFactory(new PropertyValueFactory<>("brandId"));
    }

    private void loadProductsToTable() {
        List<Product> productList = ProductDAO.getAllProducts();
        ObservableList<Product> observableList = FXCollections.observableArrayList(productList);
        if (productTableView != null) {
            productTableView.setItems(observableList);
        }
    }

    private void setupTableClickListener() {
        productTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (productIdTextField != null) {
                    productIdTextField.setText(newSelection.getProductId());
                }
                if (nameTextField != null) {
                    nameTextField.setText(newSelection.getName());
                }
            }
        });
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

    private void filterStoresByBrand() {
        Brand selectedBrand = brandComboBox.getValue();
        if (selectedBrand != null && storeComboBox != null) {
            List<BrandStore> storeList = BrandStoreDAO.getStoresByBrand(selectedBrand.getBrandId());
            ObservableList<BrandStore> observableList = FXCollections.observableArrayList(storeList);
            storeComboBox.setItems(observableList);
            storeComboBox.setConverter(new StringConverter<BrandStore>() {
                @Override
                public String toString(BrandStore store) {
                    return store != null ? "Store #" + store.getStoreNo() + " - " + store.getAddress() : "";
                }

                @Override
                public BrandStore fromString(String string) {
                    return null;
                }
            });
        }
    }

    private void loadAllStoresToComboBox() {
        List<BrandStore> storeList = BrandStoreDAO.getAllBrandStores();
        ObservableList<BrandStore> observableList = FXCollections.observableArrayList(storeList);
        storeComboBox.setItems(observableList);
        storeComboBox.setConverter(new StringConverter<BrandStore>() {
            @Override
            public String toString(BrandStore store) {
                return store != null ? "Store #" + store.getStoreNo() + " (" + store.getBrandId() + ") - " + store.getAddress() : "";
            }

            @Override
            public BrandStore fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    public void refreshTable() {
        loadProductsToTable();
        if (successLabel != null) {
            successLabel.setText("Table refreshed!");
            successLabel.setTextFill(Color.GREEN);
        }
    }
}