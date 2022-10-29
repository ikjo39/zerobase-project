<%@ page import="com.example.zerobaseproject1.service.InsertService" %>
<%@ page import="com.example.zerobaseproject1.service.InsertService" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <style>
        body {
            text-align: center;
        }
    </style>
</head>
<body>
<%



    InsertService insertService = new InsertService();
    insertService.Insert();


    int maxCnt = insertService.maxCount();
%>
<h1>
    <%=maxCnt%>개의 WIFI 정보를 정상적으로 저장하였습니다.
</h1>
<p><a href="Home.jsp">홈 으로 가기</a></p>
</body>
</html>