<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en" xmlns="">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Лабораторная работа №2</title>
    <link rel="stylesheet" href="style.css">
    <link rel="icon" type="image/x-icon" href="favicon.ico">
</head>
<body>
<div class="header">
    <h1>Яснов Михаил Андреевич</h1>
    <h3>Группа Веб 17, Вариант 51511</h3>
</div>

<div class="container">
    <div class="left-block" id="graph">
        <jsp:include page="graph.jsp"/>
    </div>

    <div class="right-block">
        <jsp:include page="selectors.jsp"/>
    </div>
</div>

<jsp:include page="table.jsp"/>

<script src="main.js"></script>
</body>
</html>