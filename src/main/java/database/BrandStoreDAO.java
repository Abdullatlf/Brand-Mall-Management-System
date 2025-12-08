package database;

import Exceptions.StoreHasProductsException;
import models.BrandStore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandStoreDAO {

    public static List<BrandStore> getAllBrandStores() {
        List<BrandStore> storeList = new ArrayList<>();
        String query = "SELECT * FROM Brand_Stores";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int storeNo = resultSet.getInt("StoreNo");
                String address = resultSet.getString("Address").trim();
                String brandId = resultSet.getString("BRAND_ID").trim();
                String managerId = resultSet.getString("MANAGER_ID").trim();
                String mallId = resultSet.getString("MALL_ID").trim();
                BrandStore store = new BrandStore(storeNo, address, brandId, managerId, mallId);
                storeList.add(store);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return storeList;
    }

    public static List<BrandStore> getStoresByBrand(String brandId) {
        List<BrandStore> storeList = new ArrayList<>();
        String query = "SELECT * FROM Brand_Stores WHERE BRAND_ID = ?";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, brandId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int storeNo = resultSet.getInt("StoreNo");
                String address = resultSet.getString("Address").trim();
                String managerId = resultSet.getString("MANAGER_ID").trim();
                String mallId = resultSet.getString("MALL_ID").trim();
                BrandStore store = new BrandStore(storeNo, address, brandId, managerId, mallId);
                storeList.add(store);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return storeList;
    }

    public static boolean addBrandStore(int storeNo, String address, String brandId, String managerId, String mallId) throws SQLException {
        String query = "INSERT INTO Brand_Stores (StoreNo, Address, BRAND_ID, MANAGER_ID, MALL_ID) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, storeNo);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, brandId);
        preparedStatement.setString(4, managerId);
        preparedStatement.setString(5, mallId);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }

    public static BrandStore searchBrandStore(int storeNo, String brandId) throws SQLException {
        String query = "SELECT * FROM Brand_Stores WHERE StoreNo = ? AND BRAND_ID = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, storeNo);
        preparedStatement.setString(2, brandId);
        ResultSet resultSet = preparedStatement.executeQuery();
        BrandStore store;
        if (resultSet.next()) {
            String address = resultSet.getString("Address").trim();
            String managerId = resultSet.getString("MANAGER_ID").trim();
            String mallId = resultSet.getString("MALL_ID").trim();
            store = new BrandStore(storeNo, address, brandId, managerId, mallId);
        } else {
            return null;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return store;
    }

    public static boolean deleteBrandStore(int storeNo, String brandId) throws StoreHasProductsException {
        String deleteQuery = "DELETE FROM Brand_Stores WHERE StoreNo = ? AND BRAND_ID = ?";
        String checkQuery = "SELECT COUNT(*) FROM PRODUCTS WHERE StoreNo = ? AND BRAND_ID = ?";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement checkPreparedStatement = connection.prepareStatement(checkQuery);
            checkPreparedStatement.setInt(1, storeNo);
            checkPreparedStatement.setString(2, brandId);
            ResultSet checkResultSet = checkPreparedStatement.executeQuery();
            if (checkResultSet.next() && checkResultSet.getInt("COUNT(*)") > 0) {
                checkPreparedStatement.close();
                connection.close();
                throw new StoreHasProductsException("Cannot delete store: still has products assigned");
            }
            PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteQuery);
            deletePreparedStatement.setInt(1, storeNo);
            deletePreparedStatement.setString(2, brandId);
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

    public static boolean updateBrandStore(int storeNo, String brandId, String address, String managerId, String mallId) throws SQLException {
        String query = "UPDATE Brand_Stores SET Address = ?, MANAGER_ID = ?, MALL_ID = ? WHERE StoreNo = ? AND BRAND_ID = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, address);
        preparedStatement.setString(2, managerId);
        preparedStatement.setString(3, mallId);
        preparedStatement.setInt(4, storeNo);
        preparedStatement.setString(5, brandId);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }
}