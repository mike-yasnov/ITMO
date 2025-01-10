function updateY(y) {
    if (isNaN(parseFloat(y))){
        document.getElementById("myForm:yInput").value = ""
        return
    }
    console.log("Y: " + parseFloat(y))
    document.getElementById("myForm:yH").value = y
}

function clickCheckButton() {
    // Используем querySelector для поиска элементов по id
    document.querySelectorAll("[id^='myForm:xBox']").forEach(xBox => {
        if (xBox.checked) {
            // Для каждого чекбокса xBox ищем совпадения с чекбоксами rBox
            document.querySelectorAll("[id^='myForm:rBox']").forEach(rBox => {
                if (rBox.checked) {
                    // Регулярные выражения для извлечения значений из id
                    const x = xBox.id.split('_')[1];
                    const r = rBox.id.split('-').slice(1).join('.').split('_')[0]


                    const xValue = parseFloat(x);  // Число из xBox
                    const rValue = parseFloat(r);  // Число из rBox

                    console.log(xValue, rValue)

                    // Присваиваем значения в соответствующие поля
                    document.getElementById("myForm:xH").value = xValue;
                    document.getElementById("myForm:rH").value = rValue;

                    // Кликаем по кнопке
                    document.getElementById("myForm:checkButton").click();
                }
            });
        }
    });
}

document.getElementById('graph-svg').addEventListener('click', function (event) {
    const svgElement = document.getElementById('graph-svg');
    const rect = svgElement.getBoundingClientRect();
    const xGraph = event.clientX - rect.left;
    const yGraph = event.clientY - rect.top;

    document.querySelectorAll("[id^='myForm:rBox']").forEach(rBox => {
        if (rBox.checked) {
            const r = rBox.id.split('-').slice(1).join('.').split('_')[0]
            document.getElementById("myForm:rH").value = r
            const x = ((xGraph - 150) / 100 * r).toFixed(2);
            const y = ((yGraph - 150) / 100 * -1 * r).toFixed(2);

            document.getElementById("myForm:xH").value = x
            document.getElementById("myForm:yH").value = y
            document.getElementById("myForm:checkButton").click()
        }
    })
});

function draw(x, y, r, isHit) {
    const svgElement = document.getElementById('graph-svg');
    const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    circle.setAttribute("cx", (parseFloat(x) / parseFloat(r) * 100 + 150).toFixed(2));
    circle.setAttribute("cy", (parseFloat(y) / parseFloat(r) * -100 + 150).toFixed(2));
    circle.setAttribute("r", 3);
    circle.setAttribute("fill", isHit ? "#00ffff" : "#e5080c");
    svgElement.appendChild(circle);
}

function clear() {
    const svgElement = document.getElementById('graph-svg');
    const circles = svgElement.getElementsByTagName('circle');
    circles.forEach(circle => {
        svgElement.removeChild(circle);
    });
}