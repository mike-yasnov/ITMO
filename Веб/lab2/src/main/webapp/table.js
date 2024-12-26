function sendRequest(x, y, r) {
    const data = {
        x: x,
        y: y,
        r: r
    }
    const jsonFormatData = JSON.stringify(data)
    fetch("controllerServlet", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: jsonFormatData
    }).then(response => {
        if (!response.ok) {
            throw new Error('Ошибка при отправке запроса');
        }
        return response.text(); // Прочитать ответ
    })
        .then(result => {
            const table = document.getElementById("resultsTable");
            const newRow = table.insertRow(); // Добавляем новую строку

            // Заполняем ячейки новой строки
            const xCell = newRow.insertCell(0);
            const yCell = newRow.insertCell(1);
            const rCell = newRow.insertCell(2);
            const answerCell = newRow.insertCell(3);
            const executionTimeCell = newRow.insertCell(4);
            const serverTimeCell = newRow.insertCell(5);

            xCell.innerText = x;
            yCell.innerText = y;
            rCell.innerText = r;

            const jsonResult = JSON.parse(result);
            answerCell.innerText = jsonResult.isHit ? "is hit" : "miss";
            executionTimeCell.innerText = jsonResult.executionTime;
            serverTimeCell.innerText = jsonResult.serverTime;
            drawCircle(x, y, r, jsonResult.isHit);
        })
        .catch(error => {})
}