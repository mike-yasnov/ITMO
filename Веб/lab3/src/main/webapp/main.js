function showError(message) {
    // Create toast container
    const toast = document.createElement('div');
    toast.className = 'custom-toast';
    
    // Create toast content
    toast.innerHTML = `
        <div class="custom-toast-header">Ошибка</div>
        <div>${message}</div>
    `;
    
    // Add to document
    document.body.appendChild(toast);
    
    // Remove after 3 seconds
    setTimeout(() => {
        if (toast && toast.parentElement) {
            toast.parentElement.removeChild(toast);
        }
    }, 3000);
}

function validateYKeyPress(event) {
    return event.charCode === 46 || event.charCode === 45 || (event.charCode >= 48 && event.charCode <= 57);
}

function updateY(y) {
    const yInput = document.getElementById("myForm:yInput");
    
    // Remove any characters that are not digits, dots, or minus signs
    const sanitizedValue = y.replace(/[^0-9.-]/g, '');
    
    // Handle multiple dots or minus signs
    const parts = sanitizedValue.split('.');
    if (parts.length > 2) {
        // Keep only first dot
        yInput.value = parts[0] + '.' + parts.slice(1).join('');
        return;
    }
    
    // Handle multiple minus signs
    const minusSigns = (sanitizedValue.match(/-/g) || []).length;
    if (minusSigns > 1 || (minusSigns === 1 && sanitizedValue[0] !== '-')) {
        // Keep only first minus sign and ensure it's at the start
        yInput.value = sanitizedValue.replace(/-/g, '');
        if (minusSigns >= 1) {
            yInput.value = '-' + yInput.value;
        }
        return;
    }
    
    // Limit input length to 8 characters
    if (sanitizedValue.length > 8) {
        yInput.value = sanitizedValue.slice(0, 8);
        return;
    }
    
    // Update input with sanitized value if it's different
    if (y !== sanitizedValue) {
        yInput.value = sanitizedValue;
        return;
    }
    
    const yValue = parseFloat(sanitizedValue);
    
    // Check if input is a valid number
    if (isNaN(yValue)) {
        yInput.value = "";
        return;
    }
    
    // Check Y bounds
    if (yValue < -3 || yValue > 5) {
        showError('Введен неверный Y');
        return;
    }
    
    document.getElementById("myForm:yH").value = yValue;
}

function validateFormInputs() {
    // Check X
    const xSelected = Array.from(document.querySelectorAll("[id^='myForm:xBox']"))
        .some(xbox => xbox.checked);
    if (!xSelected) {
        showError('Не выбрано значение X');
        return false;
    }

    // Check Y
    const yValue = document.getElementById("myForm:yH").value;
    if (!yValue) {
        showError('Не введено значение Y');
        return false;
    }

    // Check R
    const rSelected = Array.from(document.querySelectorAll("[id^='myForm:rBox']"))
        .some(rbox => rbox.checked);
    if (!rSelected) {
        showError('Не выбрано значение R');
        return false;
    }

    return true;
}

function clickCheckButton() {
    if (!validateFormInputs()) {
        return;
    }

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

function updateGraphAxes(r) {
    // Update X axis labels
    document.querySelector('text[x="195"][y="140"]').textContent = r/2;
    document.querySelector('text[x="248"][y="140"]').textContent = r;
    document.querySelector('text[x="40"][y="140"]').textContent = -r;
    document.querySelector('text[x="90"][y="140"]').textContent = -r/2;

    // Update Y axis labels
    document.querySelector('text[x="160"][y="105"]').textContent = r/2;
    document.querySelector('text[x="160"][y="55"]').textContent = r;
    document.querySelector('text[x="160"][y="205"]').textContent = -r/2;
    document.querySelector('text[x="160"][y="255"]').textContent = -r;
}

// Flag to bypass validation for graph clicks
let isGraphClick = false;

document.getElementById('graph-svg').addEventListener('click', function (event) {
    // Check only R for graph clicks
    const rSelected = Array.from(document.querySelectorAll("[id^='myForm:rBox']"))
        .some(rbox => rbox.checked);
    if (!rSelected) {
        showError('Не выбрано значение R');
        return;
    }

    const svgElement = document.getElementById('graph-svg');
    const rect = svgElement.getBoundingClientRect();
    const xGraph = event.clientX - rect.left;
    const yGraph = event.clientY - rect.top;

    document.querySelectorAll("[id^='myForm:rBox']").forEach(rBox => {
        if (rBox.checked) {
            const r = rBox.id.split('-').slice(1).join('.').split('_')[0];
            const x = ((xGraph - 150) / 100 * r).toFixed(4);
            const y = ((yGraph - 150) / 100 * -1 * r).toFixed(4);
            
            // Check Y bounds
            if (parseFloat(y) < -3 || parseFloat(y) > 5) {
                showError('Введен неверный Y');
                return;
            }
            
            document.getElementById("myForm:xH").value = x;
            document.getElementById("myForm:yH").value = y;
            document.getElementById("myForm:rH").value = r;
            
            // Set flag before clicking
            isGraphClick = true;
            document.getElementById("myForm:checkButton").click();
            // Reset flag after clicking
            isGraphClick = false;
        }
    });
});

document.querySelectorAll("[id^='myForm:rBox']").forEach(rBox => {
    rBox.addEventListener('change', function() {
        if (this.checked) {
            const r = this.id.split('-').slice(1).join('.').split('_')[0];
            const rValue = parseFloat(r);
            updateGraphAxes(rValue);
            updatePoints(rValue);
        }
    });
});

// Store points with their original coordinates and hit status
let points = [];

function draw(x, y, r, isHit) {
    const svgElement = document.getElementById('graph-svg');
    const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    
    // Format coordinates to 4 decimal places
    const formattedX = parseFloat(x).toFixed(4);
    const formattedY = parseFloat(y).toFixed(4);
    
    // Store the point data
    points.push({ x: parseFloat(formattedX), y: parseFloat(formattedY), isHit });
    
    // Calculate position
    circle.setAttribute("cx", (formattedX / r * 100 + 150).toFixed(4));
    circle.setAttribute("cy", (formattedY / r * -100 + 150).toFixed(4));
    circle.setAttribute("r", 3);
    circle.setAttribute("fill", isHit ? "#00ffff" : "#e5080c");
    svgElement.appendChild(circle);
}

function checkHit(x, y, r) {
    // Rectangle in second quadrant
    if (x <= 0 && x >= -r/2 && y >= 0 && y <= r) {
        return true;
    }
    // Triangle in first quadrant
    if (x >= 0 && y >= 0 && y <= -2*x + r) {
        return true;
    }
    // Quarter circle in third quadrant
    if (x <= 0 && y <= 0 && (x*x + y*y) <= r*r) {
        return true;
    }
    return false;
}

function updatePoints(newR) {
    const svgElement = document.getElementById('graph-svg');
    // Remove all existing circles
    const circles = svgElement.getElementsByTagName('circle');
    while (circles.length > 0) {
        circles[0].remove();
    }
    
    // Redraw all points with new R and check hits
    points.forEach(point => {
        // Recheck hit with new R
        point.isHit = checkHit(point.x, point.y, newR);
        
        const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        circle.setAttribute("cx", (point.x / newR * 100 + 150).toFixed(4));
        circle.setAttribute("cy", (point.y / newR * -100 + 150).toFixed(4));
        circle.setAttribute("r", 3);
        circle.setAttribute("fill", point.isHit ? "#00ffff" : "#e5080c");
        svgElement.appendChild(circle);
    });
}

function clear() {
    const svgElement = document.getElementById('graph-svg');
    const circles = svgElement.getElementsByTagName('circle');
    while (circles.length > 0) {
        circles[0].remove();
    }
    // Clear the points array
    points = [];
}

// Add event listener to the check button
document.getElementById("myForm:checkButton").addEventListener("click", function(event) {
    // Skip validation if it's a graph click
    if (!isGraphClick && !validateFormInputs()) {
        event.preventDefault();
    }
});