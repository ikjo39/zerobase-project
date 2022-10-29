package com.example.zerobaseproject1.service;

import java.sql.*;

public class HistoryInsertService {

    public void insertHistory(double xCor, double yCor) throws SQLException {
        String url = "jdbc:sqlite:C:\\Users\\82108\\IdeaProjects\\zerobase-project1\\identifier.sqlite";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(url);

            String sql = String.format("INSERT INTO appUser (id, xCor, yCor) " +
                    "values ((SELECT COUNT(*) FROM appUser) + 1, ?, ?);");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, xCor);
            preparedStatement.setDouble(2, yCor);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 0) {
                System.out.println("저장 실패!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
