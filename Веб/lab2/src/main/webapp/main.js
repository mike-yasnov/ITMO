let x;
let y;
let r;
let lastSelectedR = null;

const svgElement = document.getElementById('graph-svg');
let points = [];

function checkLength(input) {
    if (input.value.length > 5) {
        input.value = input.value.slice(0, 5);
    }
}

function isEmptyString(str) {
    return !str || !str.trim();
}

function showToast(message, type = 'error') {
    const toastContainer = document.querySelector('.toast-container') || (() => {
        const container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
        return container;
    })();

    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.textContent = message;

    toastContainer.appendChild(toast);
    
    setTimeout(() => {
        toast.classList.add('show');
    }, 100);

    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => {
            toastContainer.removeChild(toast);
        }, 300);
    }, 3000);
}

document.getElementById("getButton").addEventListener("click", () => {
    y = document.getElementById("yInput").value;
    x = document.getElementById("xSelect").value;

    if (isEmptyString(y) || y < -3 || y > 3) {
        showToast("Значение Y должно быть в диапазоне [-3, 3]", "error");
        return;
    }

    const radios = document.getElementsByName("number");
    let isRSelected = false;
    for (const radio of radios) {
        if (radio.checked) {
            isRSelected = true;
            const fx = x;
            const fy = y;
            const fr = radio.value;
            sendRequest(fx, fy, fr);
        }
    }

    if (!isRSelected) {
        showToast("Выберите значение R", "error");
    }
})

function drawCircle(x, y, r, answer) {
    const svgElement = document.getElementById('graph-svg');
    const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    const cx = (parseFloat(x) * 100 / parseFloat(r)) + 150;
    const cy = (-parseFloat(y) * 100 / parseFloat(r)) + 150;
    circle.setAttribute("cx", cx);
    circle.setAttribute("cy", cy);
    circle.setAttribute("r", 5);
    if (answer) {
        circle.setAttribute("fill", "green");
    } else {
        circle.setAttribute("fill", "red");
    }
    svgElement.appendChild(circle);
    
    points.push({x: parseFloat(x), y: parseFloat(y), answer: answer});
}

async function checkPoint(x, y, r) {
    try {
        const response = await fetch('controllerServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                x: parseFloat(x),
                y: parseFloat(y),
                r: parseFloat(r)
            })
        });

        const contentType = response.headers.get('content-type');
        if (!contentType || !contentType.includes('application/json')) {
            return false;
        }

        const data = await response.json();
        return data.isHit === true;
    } catch (error) {
        return false;
    }
}

async function redrawPoints(newR) {
    if (!newR) return;
    
    const svgElement = document.getElementById('graph-svg');
    const circles = svgElement.getElementsByTagName('circle');
    while (circles.length > 1) {
        circles[1].remove();
    }
    
    const updatedPoints = [];
    
    for (const point of points) {
        try {
            const isHit = await checkPoint(point.x, point.y, newR);
            
            const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
            const cx = (point.x * 100 / parseFloat(newR)) + 150;
            const cy = (-point.y * 100 / parseFloat(newR)) + 150;
            circle.setAttribute("cx", cx);
            circle.setAttribute("cy", cy);
            circle.setAttribute("r", 5);
            circle.setAttribute("fill", isHit ? "green" : "red");
            svgElement.appendChild(circle);
            
            updatedPoints.push({x: point.x, y: point.y, answer: isHit});
        } catch (error) {
            console.error('Error processing point:', error);
        }
    }
    
    points = updatedPoints;
}

svgElement.addEventListener('click', function (event) {
    const rect = svgElement.getBoundingClientRect();
    const radios = document.getElementsByName("number");
    let isRSelected = false;

    for (const radio of radios) {
        if (radio.checked) {
            isRSelected = true;
            const fr = parseFloat(radio.value);
            const xGraph = event.clientX - rect.left;
            const yGraph = event.clientY - rect.top;
            
            const fx = ((xGraph - 150) * fr / 100).toFixed(2);
            const fy = ((150 - yGraph) * fr / 100).toFixed(2);
            
            if (fy < -3 || fy > 3) {
                showToast("Точка вне допустимого диапазона Y [-3, 3]", "error");
                return;
            }
            
            sendRequest(fx, fy, fr);
        }
    }

    if (!isRSelected) {
        showToast("Выберите значение R", "error");
    }
});

document.getElementById("xSelect").addEventListener('change', function () {
    x = this.value;
});

function updateAxisLabels(r) {
    if (!r) {
        document.getElementById('label-x-pos').textContent = "R/2";
        document.getElementById('label-x-neg').textContent = "-R/2";
        document.getElementById('label-x-max').textContent = "R";
        document.getElementById('label-x-min').textContent = "-R";
        
        document.getElementById('label-y-pos').textContent = "R/2";
        document.getElementById('label-y-neg').textContent = "-R/2";
        document.getElementById('label-y-max').textContent = "R";
        document.getElementById('label-y-min').textContent = "-R";
        return;
    }
    
    const rValue = parseFloat(r);
    document.getElementById('label-x-pos').textContent = (rValue/2).toString();
    document.getElementById('label-x-neg').textContent = (-rValue/2).toString();
    document.getElementById('label-x-max').textContent = rValue.toString();
    document.getElementById('label-x-min').textContent = (-rValue).toString();
    
    document.getElementById('label-y-pos').textContent = (rValue/2).toString();
    document.getElementById('label-y-neg').textContent = (-rValue/2).toString();
    document.getElementById('label-y-max').textContent = rValue.toString();
    document.getElementById('label-y-min').textContent = (-rValue).toString();
}

function isAnyRadioSelected() {
    const radios = document.getElementsByName("number");
    return Array.from(radios).some(radio => radio.checked);
}

function getCurrentR() {
    return lastSelectedR;
}

function uncheckOtherRadios(currentRadio) {
    const radios = document.getElementsByName("number");
    radios.forEach(radio => {
        if (radio !== currentRadio) {
            radio.checked = false;
        }
    });
}

const radios = document.getElementsByName("number");
radios.forEach(radio => {
    radio.addEventListener('change', function() {
        lastSelectedR = this.value;
        
        updateAxisLabels(lastSelectedR);
        if (lastSelectedR) {
            redrawPoints(lastSelectedR);
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    updateAxisLabels(null);
});