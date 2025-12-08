package database;

import Exceptions.BrandStillUsedException;
import models.Brand;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO {

    public static List<Brand> getAllBrands() {
        List<Brand> brandList = new ArrayList<>();
        String query = "SELECT * FROM BRAND";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String brandId = resultSet.getString("BRAND_ID").trim();
                String name = resultSet.getString("Name").trim();
                Brand brand = new Brand(brandId, name);
                brandList.add(brand);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;
    }

    public static boolean addBrand(String brandId, String name) throws SQLException {
        String query = "INSERT INTO BRAND (BRAND_ID, NAME) VALUES (?, ?)";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, brandId);
        preparedStatement.setString(2, name);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }

    public static Brand searchBrand(String brandId) throws SQLException {
        String query = "SELECT * FROM BRAND WHERE BRAND_ID = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, brandId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Brand brand;
        if(resultSet.next()) {
            String name = resultSet.getString("Name").trim();
            brand = new Brand(brandId, name);
        } else {
            return null;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return brand;
    }

    public static boolean deleteBrand(String brandId) throws BrandStillUsedException {
        String deleteQuery = "DELETE FROM BRAND WHERE BRAND_ID = ?";
        String checkQuery = "SELECT COUNT(*) FROM Brand_Stores WHERE BRAND_ID = ?";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement checkPreparedStatement = connection.prepareStatement(checkQuery);
            checkPreparedStatement.setString(1, brandId);
            ResultSet checkResultSet = checkPreparedStatement.executeQuery();
            if(checkResultSet.next() && checkResultSet.getInt("COUNT(*)") > 0) {
                checkPreparedStatement.close();
                connection.close();
                throw new BrandStillUsedException("Cannot delete brand: still has stores");
            }
            PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteQuery);
            deletePreparedStatement.setString(1, brandId);
            int row = deletePreparedStatement.executeUpdate();
            deletePreparedStatement.close();
            connection.close();
            return row == 1;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateBrand(String brandId, String name) throws SQLException {
        String query = "UPDATE BRAND SET Name = ? WHERE BRAND_ID = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, brandId);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }
}