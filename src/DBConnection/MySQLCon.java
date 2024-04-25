package DBConnection;

import java.sql.*;

public class MySQLCon {
    Connection connection;

    public MySQLCon(){
            try {
                initConnection();
                Statement statement;
                statement = connection.createStatement();
                // statement.execute("CREATE DATABASE IF NOT EXISTS PDFUtilHistory;");
                // statement.execute("USE PDFUtilHistory;");
                statement.execute("CREATE TABLE IF NOT EXISTS History ( User VARCHAR(50) , Operation VARCHAR(20) , PerformedOn DATETIME, PDF VARCHAR(5000) , Image VARCHAR(5000) );");
                statement.close();
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private void initConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/PDFUtilHistory",
                "root", "root@123");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRecord(String user, String operation, String pdf, String image){
        try {
            initConnection();
            Statement statement;
            statement = connection.createStatement();
            String query = "INSERT INTO History VALUES ('" + user + "', '" + operation + "', " + "CURRENT_TIMESTAMP" + ", '" + pdf + "', '" + image + "');";
            statement.executeUpdate(query);
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRecord(String user, String operation, String pdf){
        try {
            initConnection();
            Statement statement;
            statement = connection.createStatement();
            String query = "INSERT INTO History ( User, Operation, PerformedOn, PDF) VALUES ('" + user + "', '" + operation + "', " + "CURRENT_TIMESTAMP" + ", '" + pdf + "');";
            statement.executeUpdate(query);
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void clearHistory(){
        try {
            initConnection();
            Statement statement;
            statement = connection.createStatement();
            String query = "DELETE FROM History;";
            statement.executeUpdate(query);
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printHistory(){
        try{
            initConnection();
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("SELECT * FROM History;");

            String user, operation, pdf, image;
            Timestamp performedOn;
            while (resultSet.next()) {
                user = resultSet.getString("User");
                operation = resultSet.getString("Operation");
                performedOn = resultSet.getTimestamp("PerformedOn");
                pdf = resultSet.getString("PDF");
                image = resultSet.getString("Image");

                System.out.println(  "User: " + user + " \nOperation: " + operation + " \nPerformed on: " + performedOn + " \nPDFs involved: " + pdf + " \nImages involved: " + image + "\n");
            }
            resultSet.close();
            statement.close();
            closeConnection();
        } 
        catch (Exception e){
            e.printStackTrace();
            closeConnection();
        }
    }

    // public static void main(String[] args) {
    //     var con = new MySQLCon();
    //     con.addRecord("user1", "PDFSplit", "k.pdf, k_1.pdf, k_2.pdf");
    //     con.addRecord("user1", "PDFToImage", "k.pdf", "k_1.jpg, k_2.jpg");
    //     con.clearHistory();
    //     con.printHistory();
    // }
}
