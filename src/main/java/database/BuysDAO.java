package database;

import models.Buys;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuysDAO {

    public static List<Buys> getAllBuys() {
        List<Buys> buysList = new ArrayList<>();
        String query = "SELECT * FROM BUYS";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int invoice = resultSet.getInt("Invoice");
                String customerSsn = resultSet.getString("CUSTOMER_SSN").trim();
                String productId = resultSet.getString("PRODUCTS_PRODUCT_ID").trim();
                Buys buys = new Buys(invoice, customerSsn, productId);
                buysList.add(buys);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return buysList;
    }

    public static boolean addBuys(int invoice, String customerSsn, String productId) throws SQLException {
        String query = "INSERT INTO BUYS (Invoice, CUSTOMER_SSN, PRODUCTS_PRODUCT_ID) VALUES (?, ?, ?)";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, invoice);
        preparedStatement.setString(2, customerSsn);
        preparedStatement.setString(3, productId);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }

    public static Buys searchBuys(int invoice) throws SQLException {
        String query = "SELECT * FROM BUYS WHERE Invoice = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, invoice);
        ResultSet resultSet = preparedStatement.executeQuery();
        Buys buys;
        if (resultSet.next()) {
            String customerSsn = resultSet.getString("CUSTOMER_SSN").trim();
            String productId = resultSet.getString("PRODUCTS_PRODUCT_ID").trim();
            buys = new Buys(invoice, customerSsn, productId);
        } else {
            return null;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return buys;
    }

    public static boolean deleteBuys(int invoice) {
        String deleteQuery = "DELETE FROM BUYS WHERE Invoice = ?";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteQuery);
            deletePreparedStatement.setInt(1, invoice);
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

    public static boolean updateBuys(int invoice, String customerSsn, String productId) throws SQLException {
        String query = "UPDATE BUYS SET CUSTOMER_SSN = ?, PRODUCTS_PRODUCT_ID = ? WHERE Invoice = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, customerSsn);
        preparedStatement.setString(2, productId);
        preparedStatement.setInt(3, invoice);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }
}