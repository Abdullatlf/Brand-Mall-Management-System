package database;

import Exceptions.ProductInPurchasesException;
import models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public static List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM PRODUCTS";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String productId = resultSet.getString("PRODUCT_ID").trim();
                String name = resultSet.getString("Name").trim();
                int storeNo = resultSet.getInt("StoreNo");
                String brandId = resultSet.getString("BRAND_ID").trim();
                Product product = new Product(productId, name, storeNo, brandId);
                productList.add(product);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return productList;
    }

    public static boolean addProduct(String productId, String name, int storeNo, String brandId) throws SQLException {
        // Check if the store exists for this brand
        String checkStoreQuery = "SELECT COUNT(*) FROM Brand_Stores WHERE StoreNo = ? AND BRAND_ID = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement checkStatement = connection.prepareStatement(checkStoreQuery);
        checkStatement.setInt(1, storeNo);
        checkStatement.setString(2, brandId);
        ResultSet rs = checkStatement.executeQuery();
        if (rs.next() && rs.getInt("COUNT(*)") == 0) {
            checkStatement.close();
            connection.close();
            throw new SQLException("Store does not exist for the specified brand");
        }
        checkStatement.close();

        String query = "INSERT INTO PRODUCTS (PRODUCT_ID, Name, StoreNo, BRAND_ID) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, productId);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, storeNo);
        preparedStatement.setString(4, brandId);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }

    public static Product searchProduct(String productId) throws SQLException {
        String query = "SELECT * FROM PRODUCTS WHERE PRODUCT_ID = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, productId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Product product;
        if (resultSet.next()) {
            String name = resultSet.getString("Name").trim();
            int storeNo = resultSet.getInt("StoreNo");
            String brandId = resultSet.getString("BRAND_ID").trim();
            product = new Product(productId, name, storeNo, brandId);
        } else {
            return null;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return product;
    }

    public static boolean deleteProduct(String productId) throws ProductInPurchasesException {
        String deleteQuery = "DELETE FROM PRODUCTS WHERE PRODUCT_ID = ?";
        String checkQuery = "SELECT COUNT(*) FROM BUYS WHERE PRODUCTS_PRODUCT_ID = ?";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement checkPreparedStatement = connection.prepareStatement(checkQuery);
            checkPreparedStatement.setString(1, productId);
            ResultSet checkResultSet = checkPreparedStatement.executeQuery();
            if (checkResultSet.next() && checkResultSet.getInt("COUNT(*)") > 0) {
                checkPreparedStatement.close();
                connection.close();
                throw new ProductInPurchasesException("Cannot delete product: referenced in purchase records");
            }
            PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteQuery);
            deletePreparedStatement.setString(1, productId);
            int row = deletePreparedStatement.executeUpdate();
            deletePreparedStatement.close();
            connection.close();
            return row == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean updateProduct(String productId, String name, int storeNo, String brandId) throws SQLException {
        // Check if the store exists for this brand
        String checkStoreQuery = "SELECT COUNT(*) FROM Brand_Stores WHERE StoreNo = ? AND BRAND_ID = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement checkStatement = connection.prepareStatement(checkStoreQuery);
        checkStatement.setInt(1, storeNo);
        checkStatement.setString(2, brandId);
        ResultSet rs = checkStatement.executeQuery();
        if (rs.next() && rs.getInt("COUNT(*)") == 0) {
            checkStatement.close();
            connection.close();
            throw new SQLException("Store does not exist for the specified brand");
        }
        checkStatement.close();

        String query = "UPDATE PRODUCTS SET Name = ?, StoreNo = ?, BRAND_ID = ? WHERE PRODUCT_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, storeNo);
        preparedStatement.setString(3, brandId);
        preparedStatement.setString(4, productId);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }
}