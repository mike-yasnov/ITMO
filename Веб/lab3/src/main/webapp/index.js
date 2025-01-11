let serverDate;

function initializeTime() {
    // Получаем серверное время из скрытого элемента
    const serverTimeStr = document.getElementById('serverTime').textContent;
    serverDate = parseRussianDateTime(serverTimeStr);
    updateDisplay();
    // Обновляем время каждую секунду
    setInterval(updateDisplay, 1000);
}

function parseRussianDateTime(dateStr) {
    // Пример входной строки: "понедельник, 11 января 2025 г. 10:38:07"
    const parts = dateStr.match(/(\d+) (\w+) (\d{4}) г\. (\d{2}):(\d{2}):(\d{2})/);
    if (!parts) return new Date();

    const monthsRu = {
        'января': 0, 'февраля': 1, 'марта': 2, 'апреля': 3,
        'мая': 4, 'июня': 5, 'июля': 6, 'августа': 7,
        'сентября': 8, 'октября': 9, 'ноября': 10, 'декабря': 11
    };

    const [_, day, month, year, hours, minutes, seconds] = parts;
    return new Date(year, monthsRu[month], day, hours, minutes, seconds);
}

function formatDateTime(date) {
    const daysOfWeek = ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота'];
    const months = ['января', 'февраля', 'марта', 'апреля', 'мая', 'июня', 
                   'июля', 'августа', 'сентября', 'октября', 'ноября', 'декабря'];

    const dayOfWeek = daysOfWeek[date.getDay()];
    const day = date.getDate();
    const month = months[date.getMonth()];
    const year = date.getFullYear();
    const time = date.toTimeString().split(' ')[0];

    return `${dayOfWeek}, ${day} ${month} ${year} г. ${time}`;
}

function updateDisplay() {
    if (!serverDate) return;
    
    // Увеличиваем время на 1 секунду
    serverDate.setSeconds(serverDate.getSeconds() + 1);
    
    // Обновляем отображение
    document.getElementById('clientTime').textContent = formatDateTime(serverDate);
}

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', initializeTime);
