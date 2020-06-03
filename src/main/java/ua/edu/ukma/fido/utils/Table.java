package ua.edu.ukma.fido.utils;

import ua.edu.ukma.fido.Main;

import java.sql.*;

public class Table {
    public static void create() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS " + Main.tableName + " (id INTEGER, title TEXT)";

        try {
            Statement statement = DB.connection.createStatement();

            statement.execute(sqlQuery);

            System.out.println("Table " + Main.tableName + " created");
            System.out.println();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void insert(int id, String title) {
        String sqlQuery = "INSERT INTO " + Main.tableName +  " (id, title) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = DB.connection.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);

            preparedStatement.executeUpdate();

            System.out.println("Inserted " + id + " " + title);
            System.out.println();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void update(int id, String title) {
        String sqlQuery = "UPDATE " + Main.tableName + " SET title = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DB.connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            System.out.println("Updated " + id + " " + title);
            System.out.println();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ResultSet selectOneByTitle(String title) {
        String sqlQuery = "SELECT * FROM " + Main.tableName +  " WHERE title = ?";

        try {
            PreparedStatement preparedStatement = DB.connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, title);

            return preparedStatement.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    public static ResultSet selectOneLimitOffset(int limit, int offset) {
        String sqlQuery = "SELECT * FROM " + Main.tableName +  " LIMIT ?, ?";

        try {
            PreparedStatement preparedStatement = DB.connection.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);

            return preparedStatement.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }


    public static ResultSet selectAll() {
        String sql = "SELECT * FROM " + Main.tableName;

        try {
            Statement statement  = DB.connection.createStatement();

            return statement.executeQuery(sql);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    public static void delete(int id) {
        String sql = "DELETE FROM " + Main.tableName + " WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DB.connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            System.out.println("Deleted " + id);
            System.out.println();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void truncate() {
        String sqlQuery = "DELETE FROM " + Main.tableName;

        try {
            Statement statement = DB.connection.createStatement();

            statement.execute(sqlQuery);

            System.out.println("Table " + Main.tableName + " truncated");
            System.out.println();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
