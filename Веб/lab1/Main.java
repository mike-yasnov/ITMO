import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

class Main {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String RESULT_JSON = """
            {
                "answer": %b,
                "executionTime": "%s",
                "serverTime": "%s"
            }
            """;
    private static final String HTTP_RESPONSE = """
            HTTP/1.1 200 OK
            Content-Type: application/json
            Content-Length: %d
            
            %s
            """;
    private static final String HTTP_ERROR = """
            HTTP/1.1 400 Bad Request
            Content-Type: application/json
            Content-Length: %d
            
            %s
            """;
    private static final String ERROR_JSON = """
            {
                "reason": "%s"
            }
            """;

    public static void main(String[] args) throws IOException {
        var fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            long startTime = System.nanoTime();
            try {
                var queryParams = System.getProperties().getProperty("QUERY_STRING");

                Map<String, String> params = parseQueryParams(queryParams);

                // Валидация параметров
                String validationError = validateParams(params);
                if (validationError != null) {
                    sendErrorResponse(validationError);
                    continue;
                }

                int x = Integer.parseInt(params.get("x"));
                double y = Double.parseDouble(params.get("y"));
                double r = Double.parseDouble(params.get("r"));

                boolean insideRectangle = isInsideRectangle(x, y, 0, -r, r, r);
                boolean insidePolygon = isInsidePolygon(x, y, r);
                boolean insidePath = isInsidePath(x, y, r);

                var json = String.format(RESULT_JSON, insideRectangle || insidePolygon || insidePath,
                        (System.nanoTime() - startTime) / 1000,
                        LocalDateTime.now().format(formatter));
                sendResponse(json);

            } catch (Exception ex) {
                sendErrorResponse(ex.getMessage());
            }
        }
    }

    private static Map<String, String> parseQueryParams(String queryParams) {
        Map<String, String> params = new HashMap<>();
        if (queryParams != null && !queryParams.isEmpty()) {
            String[] pairs = queryParams.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }

    private static String validateParams(Map<String, String> params) {
        // Проверка X
        int x;
        try {
            x = Integer.parseInt(params.get("x"));
            if (x < -3 || x > 5) {
                return "Некорректное значение X. Ожидается значение от -3 до 5.";
            }
        } catch (NumberFormatException e) {
            return "Некорректное значение X. Ожидается целое число.";
        }

        // Проверка Y
        double y;
        try {
            y = Double.parseDouble(params.get("y"));
            if (y < -5 || y > 3 || params.get("y").length() > 15) {
                return "Некорректное значение Y. Ожидается число с не более чем 15 знаками, в диапазоне от -5 до 3.";
            }
        } catch (NumberFormatException e) {
            return "Некорректное значение Y. Ожидается число.";
        }

        // Проверка R
        double r;
        try {
            r = Double.parseDouble(params.get("r"));
            if (r != 1 && r != 1.5 && r != 2 && r != 2.5 && r != 3) {
                return "Некорректное значение R. Ожидается одно из значений: 1, 1.5, 2, 2.5, 3.";
            }
        } catch (NumberFormatException e) {
            return "Некорректное значение R. Ожидается число.";
        }

        return null; // Параметры валидны
    }

    private static void sendResponse(String json) throws IOException {
        var responseBody = json.trim();
        var response = String.format(HTTP_RESPONSE, responseBody.getBytes(StandardCharsets.UTF_8).length, responseBody);
        FCGIInterface.request.outStream.write(response.getBytes(StandardCharsets.UTF_8));
        FCGIInterface.request.outStream.flush();
    }

    private static void sendErrorResponse(String errorMessage) throws IOException {
        var json = String.format(ERROR_JSON, errorMessage);
        var responseBody = json.trim();
        var response = String.format(HTTP_ERROR, responseBody.getBytes(StandardCharsets.UTF_8).length, responseBody);
        FCGIInterface.request.outStream.write(response.getBytes(StandardCharsets.UTF_8));
        FCGIInterface.request.outStream.flush();
    }

    private static boolean isInsideRectangle(double px, double py, double rectX, double rectY, double width, double height) {
        return (px >= rectX && px <= rectX + width) && (py >= rectY && py <= rectY + height);
    }

    private static boolean isInsidePolygon(double px, double py, double r) {
        double[][] polygon = {
                {-r, 0},
                {0, 0},
                {0, -r}
        };

        int n = polygon.length;
        boolean inside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = polygon[i][0], yi = polygon[i][1];
            double xj = polygon[j][0], yj = polygon[j][1];
            boolean intersect = ((yi > py) != (yj > py)) && (px < (xj - xi) * (py - yi) / (yj - yi) + xi);
            if (intersect) {
                inside = !inside;
            }
        }

        return inside;
    }

    private static boolean isInsidePath(double x, double y, double r) {
        double centerX = 0;
        double centerY = 0;

        if (x <= centerX && y >= centerY && x >= (centerX - r) && y <= (centerY + r)) {
            double distanceSquared = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
            return distanceSquared <= Math.pow(r, 2);
        }
        return false;
    }
}
