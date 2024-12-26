<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Лаба №2</title>
    <link rel="stylesheet" href="style.css">
    <link rel="icon" type="image/x-icon" href="favicon.ico">
</head>
<body>
<h4 class="input">Выберете X</h4>
<select id="xSelect">
    <option value="-3">-3</option>
    <option value="-2">-2</option>
    <option value="-1">-1</option>
    <option value="0">0</option>
    <option value="1">1</option>
    <option value="2">2</option>
    <option value="3">3</option>
    <option value="4">4</option>
    <option value="5">5</option>
</select>
<h4 class="input">Введите Y</h4>
<input type="number" id="yInput" step="any" min="-3" max="3" oninput="checkLength(this)" placeholder="Введите y"/>

<h4 class="input">Выберете R</h4>
<div id="radios">
    <label>
        <input type="checkbox" name="number" value="1" aria-selected="true"> 1
    </label><br>
    <label>
        <input type="checkbox" name="number" value="2"> 2
    </label><br>
    <label>
        <input type="checkbox" name="number" value="3"> 3
    </label><br>
    <label>
        <input type="checkbox" name="number" value="4"> 4
    </label><br>
    <label>
        <input type="checkbox" name="number" value="5"> 5
    </label><br>
</div>
<div>
    <button id="getButton">Проверить</button>
</div>
</body>
</html>
