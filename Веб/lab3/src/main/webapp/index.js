function updateClock() {
    const now = new Date();
    const options = {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
        second: 'numeric',
        timeZoneName: 'short'
    };
    const formattedTime = now.toLocaleString('ru-RU', options); // Используем локализованный формат
    document.getElementById('clock').textContent = formattedTime;
}

updateClock(); // Первоначальное отображение времени
setInterval(updateClock, 5000); // Обновляем каждые 5 секунд
