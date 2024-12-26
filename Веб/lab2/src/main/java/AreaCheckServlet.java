import com.google.gson.Gson;
import recource.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkAreaServlet")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Double x = (Double) request.getAttribute("x");
        Double y = (Double) request.getAttribute("y");
        Double r = (Double) request.getAttribute("r");
        System.out.println(x + " " + y + " " + r);
        long startTime = System.nanoTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        boolean isInArea = checkArea(x, y, r);

        Result result = new Result(String.valueOf(x),
                String.valueOf(y), String.valueOf(r), isInArea, System.nanoTime() - startTime, formatter.format(LocalDateTime.now()));


        List<Result> results = (List<Result>) request.getSession().getAttribute("points");
        if (results != null) {
            results.add(result);
        } else {
            results = new ArrayList<>();
            results.add(result);
            request.getSession().setAttribute("points", results);
        }

        // Response with JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(result));
    }

    private boolean checkArea(double x, double y, double r) {
        boolean insideRectangle = isInsideRectangle(x, y, r);
        boolean insidePolygon = isInsidePolygon(x, y, r);
        boolean insidePath = isInsidePath(x, y, r);
        return insideRectangle || insidePolygon || insidePath;
    }

    private static boolean isInsideRectangle(double x, double y, double r) {
        return x <= 0 && x >= -r / 2 && y >= 0 && y <= r;
    }

    private static boolean isInsidePolygon(double px, double py, double r) {
        double[][] polygon = {
                {0, 0},
                {r / 2, 0},
                {0, r / 2}
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
        return x >= 0 && y <= 0 && ((x * x + y * y) <= r/2 * r/2);
    }
}

