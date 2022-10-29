package com.example.zerobaseproject1.service;

import com.example.zerobaseproject1.Distance;
import com.example.zerobaseproject1.dto.WifiInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectService {

    public List<WifiInfo> listWifiInfo(double xCor, double yCor) {

        List<WifiInfo> wifiInfoList = new ArrayList<>();

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

            String sql = String.format("SELECT manageNum, region, wifiName, roadAddr, detailedAddr, insPlace, insType, insDepartment, serviceClass, netType, insYear, inOutClass, accessEnv, xCor, yCor, operateDate " +
                    "FROM wifiInfo " +
                    "WHERE ROUND(xCor, 2) = ? AND Round(yCor, 2) = ? " +
                    "LIMIT 20;");

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, Math.round(xCor * 100) / 100.0);
            preparedStatement.setDouble(2, Math.round(yCor * 100) / 100.0);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                WifiInfo wifiInfo = new WifiInfo();
                wifiInfo.setDistance(new Distance().distance(xCor, yCor, rs.getDouble("xCor"), rs.getDouble("yCor")));
                wifiInfo.setManageNum(rs.getString("manageNum"));
                wifiInfo.setRegion(rs.getString("region"));
                wifiInfo.setWifiName(rs.getString("wifiName"));
                wifiInfo.setRoadAddr(rs.getString("roadAddr"));
                wifiInfo.setDetailedAddr(rs.getString("detailedAddr"));
                wifiInfo.setInsPlace(rs.getString("insPlace"));
                wifiInfo.setInsType(rs.getString("insType"));
                wifiInfo.setInsDepartment(rs.getString("insDepartment"));
                wifiInfo.setServiceClass(rs.getString("serviceClass"));
                wifiInfo.setNetType(rs.getString("netType"));
                wifiInfo.setInsYear(rs.getString("insYear"));
                wifiInfo.setInOutClass(rs.getString("inOutClass"));
                wifiInfo.setAccessEnv(rs.getString("accessEnv"));
                wifiInfo.setXCor(rs.getDouble("xCor"));
                wifiInfo.setYCor(rs.getDouble("yCor"));
                wifiInfo.setOperateDate(rs.getString("operateDate"));

                wifiInfoList.add(wifiInfo);
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
        return wifiInfoList;
    }
}
