<%@ page import="com.example.zerobaseproject1.dto.WifiInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.zerobaseproject1.service.HistoryInsertService" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
    <script src="http://code.jquery.com/jquery-1.11.0.js"></script>
    <script type="text/JavaScript">
        function input_Text() {
            //위치 정보를 얻기
            navigator.geolocation.getCurrentPosition(function (pos) {
                const latitude = pos.coords.latitude;
                const longitude = pos.coords.longitude;
                document.getElementById("LAT").value = latitude;
                document.getElementById("LNT").value = longitude;
            });
        }

    </script>

    <h1>
        와이파이 정보 구하기
    </h1>
    <p>
        <a href="Home.jsp">홈</a> | <a href="siteHistory.jsp">위치 히스토리 목록</a> | <a href="GetWifiInfo.jsp">Open API 와이파이 정보
        가져오기</a>
    </p>
    <form action="selectNearWifi" method="get">
        <p>
            <label for="LAT">LAT:</label>
            <%
            if (request.getAttribute("lat") != null && request.getAttribute("lnt") != null) {

            %>
            <input type="text" id="LAT" name="latitude" maxlength="10">
            <label for="LNT"> LNT:</label>
            <input type="text" id="LNT" name="longitude" maxlength="10">
        <% } else {

            %>
            <input type="text" id="LAT" name="latitude" maxlength="10" value="0.0">
            <label for="LNT"> LNT:</label>
            <input type="text" id="LNT" name="longitude" maxlength="10" value="0.0">
    <%
        }
    %>
            <input type="button" id="getLocation" onclick='input_Text()' value = "내 위치 가져오기">
            <button type="submit" onclick="
            <%
                HistoryInsertService historyInsertService = new HistoryInsertService();
                try {
                    if (request.getAttribute("lat") != null && request.getAttribute("lnt") != null) {
                        historyInsertService.insertHistory((Double) request.getAttribute("lat"), (Double) request.getAttribute("lnt"));
                    } else {
                        historyInsertService.insertHistory(0.0, 0.0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            %>">근처 Wifi 정보 보기</button>
        </p>
    </form>

    <table>
        <thead>
        <tr>
            <td>거리(Km)</td>
            <td>관리번호</td>
            <td>자치구</td>
            <td>와이파이명</td>
            <td>도로명주소</td>
            <td>상세주소</td>
            <td>설치위치(층)</td>
            <td>설치유형</td>
            <td>설치기관</td>
            <td>서비스구분</td>
            <td>망종류</td>
            <td>설치년도</td>
            <td>실내외구분</td>
            <td>Wifi접속환경</td>
            <td>X좌표</td>
            <td>Y좌표</td>
            <td>작업일자</td>
        </tr>
        </thead>
        <tbody class="tbodyDesign">
        <%
            List<WifiInfo> wifiList = (List<WifiInfo>) request.getAttribute("wifiList");
            if (wifiList != null){
                for (WifiInfo wifiInfo : wifiList) {
        %>
        <tr class="hoverDesign">
            <td> <%=wifiInfo.getDistance()%> </td>
            <td> <%=wifiInfo.getManageNum()%> </td>
            <td> <%=wifiInfo.getRegion()%> </td>
            <td> <%=wifiInfo.getWifiName()%> </td>
            <td> <%=wifiInfo.getRoadAddr()%> </td>
            <td> <%=wifiInfo.getDetailedAddr()%> </td>
            <td> <%=wifiInfo.getInsPlace()%> </td>
            <td> <%=wifiInfo.getInsType()%> </td>
            <td> <%=wifiInfo.getInsDepartment()%> </td>
            <td> <%=wifiInfo.getServiceClass()%> </td>
            <td> <%=wifiInfo.getNetType()%> </td>
            <td> <%=wifiInfo.getInsYear()%> </td>
            <td> <%=wifiInfo.getInOutClass()%> </td>
            <td> <%=wifiInfo.getAccessEnv()%> </td>
            <td> <%=wifiInfo.getXCor()%> </td>
            <td> <%=wifiInfo.getYCor()%> </td>
            <td> <%=wifiInfo.getOperateDate()%> </td>
        </tr>

        <%
                }
            } else {
        %>
        <tr>
            <th colspan=17>위치 정보를 입력한 후에 조회해 주세요.</th>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</body>
</html>