package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Navigation methods for all entities
    public void MANAGER_btn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MANAGER/MANAGER.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void CUSTOMER_btn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CUSTOMER/CUSTOMER.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void MALL_btn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MALL/MALL.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void BRANDSTORE_btn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRANDSTORE/BRANDSTORE.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void PRODUCT_btn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/PRODUCT/PRODUCT.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void BUYS_btn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BUYS/BUYS.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void BRAND_btn(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/BRAND/BRAND.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Utility methods for showing alerts
    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void exitApplication(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}