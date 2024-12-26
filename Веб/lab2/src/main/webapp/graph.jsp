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
<section class="block plot-section">
    <div class="graph">
        <svg height="300" width="300" xmlns="http://www.w3.org/2000/svg" id="graph-svg">
            <!-- Оси со стрелками -->
            <line stroke="gray" x1="0" x2="300" y1="150" y2="150"></line>
            <line stroke="gray" x1="150" x2="150" y1="0" y2="300"></line>
            <polygon fill="white" points="150,0 144,15 156,15" stroke="white"></polygon>
            <polygon fill="white" points="300,150 285,156 285,144" stroke="white"></polygon>

            <!-- Засечки -->
            <line stroke="gray" x1="200" x2="200" y1="155" y2="145"></line>
            <line stroke="gray" x1="250" x2="250" y1="155" y2="145"></line>

            <line stroke="gray" x1="50" x2="50" y1="155" y2="145"></line>
            <line stroke="gray" x1="100" x2="100" y1="155" y2="145"></line>

            <line stroke="gray" x1="145" x2="155" y1="100" y2="100"></line>
            <line stroke="gray" x1="145" x2="155" y1="50" y2="50"></line>

            <line stroke="gray" x1="145" x2="155" y1="200" y2="200"></line>
            <line stroke="gray" x1="145" x2="155" y1="250" y2="250"></line>

            <!-- Подписи к засечкам    -->
            <text fill="white" x="195" y="140" id="label-x-pos">R/2</text>
            <text fill="white" x="248" y="140" id="label-x-max">R</text>

            <text fill="white" x="40" y="140" id="label-x-min">-R</text>
            <text fill="white" x="90" y="140" id="label-x-neg">-R/2</text>

            <text fill="white" x="160" y="105" id="label-y-pos">R/2</text>
            <text fill="white" x="160" y="55" id="label-y-max">R</text>

            <text fill="white" x="160" y="205" id="label-y-neg">-R/2</text>
            <text fill="white" x="160" y="255" id="label-y-min">-R</text>

            <text fill="white" x="160" y="10">Y</text>
            <text fill="white" x="290" y="140">X</text>

            <!-- Прямоугольник -->
            <rect x="100" y="50" width="50" height="100" fill="#318F1CFF" fill-opacity="0.2"
                  stroke="#318F1CFF"></rect>

            <!-- Треугольник -->
            <polygon fill="#318F1CFF" fill-opacity="0.2" points="150,150 150,100 200,150"
                     stroke="#318F1CFF"></polygon>

            <!-- Четверть круга -->
            <path d="M 200 150 A 100, 100,0, 0, 1, 150 200 L 150 150 Z" fill-opacity="0.2" fill="#318F1CFF"
                  stroke="#318F1CFF"></path>

            <!-- Центр оси координат -->
            <circle cx="150" cy="150" id="target-dot" r="0" stroke="white" fill="white"></circle>
        </svg>
    </div>
</section>
</body>
</html>
