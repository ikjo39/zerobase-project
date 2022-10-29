package com.example.zerobaseproject1.controller;

import com.example.zerobaseproject1.dto.WifiInfo;
import com.example.zerobaseproject1.service.SelectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectNearWifi")
public class DbController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request
            , HttpServletResponse response
    ) throws ServletException, IOException {

        double lat = Double.parseDouble(request.getParameter("latitude"));
        double lnt = Double.parseDouble(request.getParameter("longitude"));

        List<WifiInfo> result = new SelectService().listWifiInfo(lat, lnt);

        System.out.println(result);
        request.setAttribute("wifiList", result);
        request.setAttribute("lat", lat);
        request.setAttribute("lnt", lnt);

        request.getRequestDispatcher("Home.jsp").forward(request, response);
    }

}

