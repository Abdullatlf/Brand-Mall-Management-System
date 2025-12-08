package database;

import Exceptions.MallHasStoresException;
import models.Mall;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MallDAO {

    public static List<Mall> getAllMalls() {
        List<Mall> mallList = new ArrayList<>();
        String query = "SELECT * FROM MALL";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String mallId = resultSet.getString("MALL_ID").trim();
                String location = resultSet.getString("Location").trim();
                String name = resultSet.getString("Name").trim();
                Mall mall = new Mall(mallId, location, name);
                mallList.add(mall);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return mallList;
    }

    public static boolean addMall(String mallId, String location, String name) throws SQLException {
        String query = "INSERT INTO MALL (MALL_ID, Location, Name) VALUES (?, ?, ?)";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, mallId);
        preparedStatement.setString(2, location);
        preparedStatement.setString(3, name);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }

    public static Mall searchMall(String mallId) throws SQLException {
        String query = "SELECT * FROM MALL WHERE MALL_ID = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, mallId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Mall mall;
        if (resultSet.next()) {
            String location = resultSet.getString("Location").trim();
            String name = resultSet.getString("Name").trim();
            mall = new Mall(mallId, location, name);
        } else {
            return null;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return mall;
    }

    public static boolean deleteMall(String mallId) throws MallHasStoresException {
        String deleteQuery = "DELETE FROM MALL WHERE MALL_ID = ?";
        String checkQuery = "SELECT COUNT(*) FROM Brand_Stores WHERE MALL_ID = ?";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement checkPreparedStatement = connection.prepareStatement(checkQuery);
            checkPreparedStatement.setString(1, mallId);
            ResultSet checkResultSet = checkPreparedStatement.executeQuery();
            if (checkResultSet.next() && checkResultSet.getInt("COUNT(*)") > 0) {
                checkPreparedStatement.close();
                connection.close();
                throw new MallHasStoresException("Cannot delete mall: still has stores assigned");
            }
            PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteQuery);
            deletePreparedStatement.setString(1, mallId);
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

    public static boolean updateMall(String mallId, String location, String name) throws SQLException {
        String query = "UPDATE MALL SET Location = ?, Name = ? WHERE MALL_ID = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, location);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, mallId);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }
}