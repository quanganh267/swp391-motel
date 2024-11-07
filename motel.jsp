<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sách Motel</title>
</head>
<body>
    <h1>Danh sách Motel</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Tên Motel</th>
            <th>Mô tả</th>
            <th>Địa chỉ</th>
            <th>Thành phố</th>
            <th>Số phòng</th>
        </tr>
        <c:forEach var="motel" items="${motels}">
            <tr>
                <td>${motel.motelId}</td>
                <td>${motel.motelName}</td>
                <td>${motel.description}</td>
                <td>${motel.address}</td>
                <td>${motel.city}</td>
                <td>${motel.numberOfRoom}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html> 