package database;

import Exceptions.CustomerHasPurchasesException;
import models.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public static List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String query = "SELECT * FROM CUSTOMER";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String ssn = resultSet.getString("SSN").trim();
                String phone = resultSet.getString("Phone").trim();
                String name = resultSet.getString("Name").trim();
                Customer customer = new Customer(ssn, phone, name);
                customerList.add(customer);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public static boolean addCustomer(String ssn, String phone, String name) throws SQLException {
        String query = "INSERT INTO CUSTOMER (SSN, PHONE, NAME) VALUES (?, ?, ?)";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, ssn);
        preparedStatement.setString(2, phone);
        preparedStatement.setString(3, name);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }

    public static Customer searchCustomer(String ssn) throws SQLException {
        String query = "SELECT * FROM CUSTOMER WHERE SSN = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, ssn);
        ResultSet resultSet = preparedStatement.executeQuery();
        Customer customer;
        if(resultSet.next()) {
            String phone = resultSet.getString("Phone").trim();
            String name = resultSet.getString("Name").trim();
            customer = new Customer(ssn, phone, name);
        } else {
            return null;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return customer;
    }

    public static boolean deleteCustomer(String ssn) throws CustomerHasPurchasesException {
        String deleteQuery = "DELETE FROM CUSTOMER WHERE SSN = ?";
        String checkQuery = "SELECT COUNT(*) FROM BUYS WHERE CUSTOMER_SSN = ?";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement checkPreparedStatement = connection.prepareStatement(checkQuery);
            checkPreparedStatement.setString(1, ssn);
            ResultSet checkResultSet = checkPreparedStatement.executeQuery();
            if(checkResultSet.next() && checkResultSet.getInt("COUNT(*)") > 0) {
                checkPreparedStatement.close();
                connection.close();
                throw new CustomerHasPurchasesException("Cannot delete customer: has purchase history");
            }
            PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteQuery);
            deletePreparedStatement.setString(1, ssn);
            int row = deletePreparedStatement.executeUpdate();
            deletePreparedStatement.close();
            connection.close();
            return row == 1;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCustomer(String ssn, String phone, String name) throws SQLException {
        String query = "UPDATE CUSTOMER SET Phone = ?, Name = ? WHERE SSN = ?";
        Connection connection = DatabaseConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, phone);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, ssn);
        int row = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return row == 1;
    }
}