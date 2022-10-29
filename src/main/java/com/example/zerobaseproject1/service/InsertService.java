package com.example.zerobaseproject1.service;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

public class InsertService {
    static final String KEY = "415445665573656f34386f78525541";

    public Integer maxCount() throws IOException {
        int startIdx = 1001;
        int endIdx = 2000;
        String apiURL = "http://openapi.seoul.go.kr:8088/" + KEY + "/json/TbPublicWifiInfo/" + startIdx + "/" + endIdx + "/";


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(apiURL)
                .get()
                .build();
        // when
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                JsonParser parser = new JsonParser();
                try {
                    JsonObject jsonObject = (JsonObject) parser.parse(body.string());
                    JsonObject tbPublicWifiInfoObject = (JsonObject) jsonObject.get("TbPublicWifiInfo");
                    JsonElement listTotalCount = tbPublicWifiInfoObject.get("list_total_count");

                    return listTotalCount.getAsInt();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            System.err.println("Error Occurred");
        }
        return null;
    }

    public void Insert() throws IOException {


        int maxIdx = maxCount();

        int mock = maxIdx / 1000;
        int left = maxIdx % 1000;

        String url = "jdbc:sqlite:C:\\Users\\82108\\IdeaProjects\\zerobase-project1\\identifier.sqlite";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        for (int i = 0; i <= mock; i++) {
            String apiURL = "http://openapi.seoul.go.kr:8088/" + KEY
                    + "/json/TbPublicWifiInfo/" + (1000 * i)
                    + "/" + ((1000 * (i + 1)) - 1) + "/";
            if (i == mock) {
                apiURL = "http://openapi.seoul.go.kr:8088/" + KEY
                        + "/json/TbPublicWifiInfo/" + (1000 * i)
                        + "/" + ((1000 * i) + left) + "/";
            }

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(apiURL)
                    .get()
                    .build();

            // when
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (body != null) {
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) parser.parse(body.string());
                    JsonObject tbPublicWifiInfoObject = (JsonObject) jsonObject.get("TbPublicWifiInfo");
                    JsonArray rowArray = (JsonArray) tbPublicWifiInfoObject.get("row");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        for (int j = 0; j < rowArray.size(); j++) {
                            JsonObject obj3 = (JsonObject) rowArray.get(j);
                            connection = DriverManager.getConnection(url);
                            String sql = String.format("INSERT INTO wifiInfo " +
                                    "(manageNum, region, wifiName, roadAddr, detailedAddr, insPlace, insType, insDepartment, serviceClass, netType, insYear, inOutClass, accessEnv, xCor, yCor, operateDate) " +
                                    "values " +
                                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setString(1,
                                    obj3.get("X_SWIFI_MGR_NO").getAsString());
                            preparedStatement.setString(2,
                                    obj3.get("X_SWIFI_WRDOFC").getAsString());
                            preparedStatement.setString(3,
                                    obj3.get("X_SWIFI_MAIN_NM").getAsString());
                            preparedStatement.setString(4,
                                    obj3.get("X_SWIFI_ADRES1").getAsString());
                            preparedStatement.setString(5,
                                    obj3.get("X_SWIFI_ADRES2").getAsString());
                            preparedStatement.setString(6,
                                    obj3.get("X_SWIFI_INSTL_FLOOR").getAsString());
                            preparedStatement.setString(7,
                                    obj3.get("X_SWIFI_INSTL_TY").getAsString());
                            preparedStatement.setString(8,
                                    obj3.get("X_SWIFI_INSTL_MBY").getAsString());
                            preparedStatement.setString(9,
                                    obj3.get("X_SWIFI_SVC_SE").getAsString());
                            preparedStatement.setString(10,
                                    obj3.get("X_SWIFI_CMCWR").getAsString());
                            preparedStatement.setString(11,
                                    obj3.get("X_SWIFI_CNSTC_YEAR").getAsString());
                            preparedStatement.setString(12,
                                    obj3.get("X_SWIFI_INOUT_DOOR").getAsString());
                            preparedStatement.setString(13,
                                    obj3.get("X_SWIFI_REMARS3").getAsString());
                            preparedStatement.setDouble(14,
                                    obj3.get("LAT").getAsDouble());
                            preparedStatement.setDouble(15,
                                    obj3.get("LNT").getAsDouble());
                            preparedStatement.setString(16,
                                    (obj3.get("WORK_DTTM").getAsString()));

                            int affectedRows = preparedStatement.executeUpdate();

                            if (affectedRows < 0) {
                                System.out.println("저장 실패!");
                            }
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
                }
            } else {
                System.err.println("Error Occurred");
            }
        }

    }
}
