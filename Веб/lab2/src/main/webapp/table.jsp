<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="recource.Result" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Лаба №2</title>
    <link rel="stylesheet" href="style.css">
    <link rel="icon" type="image/x-icon" href="favicon.ico">
</head>
<body>
<h1 class="results">Результаты</h1>
<div id="table">
    <table id="resultsTable">
        <thead>
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Hit result</th>
            <th>Execution Time</th>
            <th>Server Time</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Result> points = (List<Result>) session.getAttribute("points");
            if (points != null) {
                for (Result point : points) {
        %>
        <tr>
            <td><%= point.getX() %></td>
            <td><%= point.getY() %></td>
            <td><%= point.getR() %></td>
            <td><%= point.getIsHit() ? "hit" : "miss" %></td>
            <td><%= point.getTime() %></td>
            <td><%= point.getServerTime() %></td>
        </tr>
        <%
            }
        } else {
        %>
        <%
            }
        %>
        </tbody>
    </table>
</div>
<script src="table.js"></script>
</body>
</html>
