<%@ page import="com.example.zerobaseproject1.dto.AppUser" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.zerobaseproject1.service.HistorySelectService" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>와이파이 정보 구하기</title>
        <style>
            table {
                width: 100%;
                height: 50px;
                border-collapse: collapse;
            }

            thead {
                height: 50px;
                text-align: center;
                color: white;
                background-color: #42ac6e;
            }

            th {
                height: 100px;
                column-span: all;
                text-align: center;
                border: 1px solid #FFFFFF ;
            }
            td {
                height: 50px;
                text-align: center;
                font-size: 0.8em;
                border: thin solid #FFFFFF ;
            }
            .hoverDesign:nth-of-type(even) {
                background: #DDD;
            }
            .hoverDesign:hover {
                background-color:#CCCCCC;
            }

        </style>
    </head>
    <body>
        <h1>
            위치 히스토리 목록
        </h1>
        <p>
        <a href="Home.jsp">홈</a> | <a href="siteHistory.jsp">위치 히스토리 목록</a> | <a href="GetWifiInfo.jsp">Open API 와이파이 정보 가져오기</a>
        </p>
        <table>
            <thead>
                <tr>
                    <td>ID</td>
                    <td>X좌표</td>
                    <td>Y좌표</td>
                    <td>조회일자</td>
                    <td>비고</td>
                </tr>
            </thead>
            <tbody class="tbodyDesign">
            <%
                List<AppUser> appUserList = new HistorySelectService().appUserList();
                if (appUserList != null) {
                    for (AppUser appUser : appUserList) {
            %>
                <tr class="hoverDesign">
                    <td><%=appUser.getId()%></td>
                    <td><%=appUser.getXCor()%></td>
                    <td><%=appUser.getYCor()%></td>
                    <td><%=appUser.getCreatedAt()%></td>
                    <td><input type="button" value="삭제"></td>
                </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>


    </body>
</html>