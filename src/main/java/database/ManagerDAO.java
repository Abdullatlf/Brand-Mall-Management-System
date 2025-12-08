package database;
import Exceptions.ManagerStillAssignedException;
import models.Manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {


    public static List<Manager> getAllManagers(){
        List<Manager> managerList = new ArrayList<>();
        String query = "SELECT * FROM MANAGER";
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String managerId = resultSet.getString("MANAGER_ID").trim();
                String name = resultSet.getString("Name").trim();
                String phone = resultSet.getString("Phone").trim();
                Manager manager = new Manager(managerId, name, phone);
                managerList.add(manager);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return managerList;
    }
    public static boolean addManager(String managerId, String name, String phone) throws SQLException{
        String query = "INSERT INTO MANAGER (MANAGER_ID, NAME, PHONE) VALUES (?, ?, ?)";
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, managerId);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,phone);
            int row = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return row == 1;
    }
    public static Manager searchManager(String managerId) throws SQLException{
        String query = "SELECT MANAGER.* FROM MANAGER WHERE MANAGER_ID = ?";
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, managerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Manager manager;
        if(resultSet.next()){
                String name = resultSet.getString("Name").trim();
                String phone = resultSet.getString("Phone").trim();
                manager = new Manager(managerId, name, phone);
            }else{
                return null;
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return manager;
    }
    public static boolean deleteManager(String managerId) throws ManagerStillAssignedException {
        String deleteQuery = "DELETE FROM MANAGER WHERE MANAGER_ID = ?";
        String checkQuery = "SELECT COUNT(*) FROM Brand_Stores WHERE MANAGER_ID = ?";
        try{
            Connection connection = DatabaseConnection.connect();
            PreparedStatement checkPreparedStatement = connection.prepareStatement(checkQuery);
            checkPreparedStatement.setString(1, managerId);
            ResultSet checkResultSet = checkPreparedStatement.executeQuery();
            if(checkResultSet.next() && checkResultSet.getInt("COUNT(*)") > 0){
                checkPreparedStatement.close();
                connection.close();
                throw new ManagerStillAssignedException("Cannot delete manager: still assigned to stores");
            }
            PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteQuery);
            deletePreparedStatement.setString(1, managerId);
            int row = deletePreparedStatement.executeUpdate();
            deletePreparedStatement.close();
            connection.close();
            return row == 1;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }
    public static boolean updateManager(String managerId, String name, String phone) throws SQLException{
        String query = "UPDATE MANAGER SET Name = ?, Phone = ? WHERE MANAGER_ID = ?";
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, managerId);
            int row = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return row == 1;
    }
}