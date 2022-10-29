package com.example.zerobaseproject1.service;

import com.example.zerobaseproject1.dto.AppUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorySelectService {

    public List<AppUser> appUserList() {

        List<AppUser> appUserList = new ArrayList<>();

        String url = "jdbc:sqlite:C:\\Users\\82108\\IdeaProjects\\zerobase-project1\\identifier.sqlite";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;


        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(url);

            String sql = String.format("SELECT id, xCor, yCor, createdAt " +
                    "FROM appUser;");

            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                AppUser appUser = new AppUser();
                appUser.setId(rs.getInt("id"));
                appUser.setXCor(rs.getDouble("xCor"));
                appUser.setYCor(rs.getDouble("yCor"));
                appUser.setCreatedAt(rs.getString("createdAt"));

                appUserList.add(appUser);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return appUserList;
    }
}
