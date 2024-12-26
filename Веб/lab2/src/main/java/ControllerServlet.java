import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/controllerServlet")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder jsonText = new StringBuilder();
        String line;

        // Получает json в текстовом виде
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null){
            jsonText.append(line);
        }

        // текст -> JSON
        JSONObject json = new JSONObject(jsonText.toString());

        // получаем значения
        Double x = json.getDouble("x");
        Double y = json.getDouble("y");
        Double r = json.getDouble("r");
        long startTime = System.nanoTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        request.setAttribute("x", x);
        request.setAttribute("y", y);
        request.setAttribute("r", r);
        request.setAttribute("time", LocalDateTime.now().format(formatter));
        request.setAttribute("executionTime", System.nanoTime()-startTime);
        request.getRequestDispatcher("checkAreaServlet").forward(request, response);
    }
}

