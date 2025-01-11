import db.PointDAO;
import db.models.PointModel;
import org.primefaces.PrimeFaces;
import utils.Validator;
import utils.TimeService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;

@ManagedBean(name = "checkPointBean")
@SessionScoped
public class CheckPointBean implements Serializable {
    @ManagedProperty("#{timeService}")
    private TimeService timeService;
    
    private boolean isHit = false;
    private PointDAO pointService = new PointDAO();
    private ArrayList<PointModel> results = (ArrayList<PointModel>) pointService.findAll();

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    public ArrayList<PointModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<PointModel> results) {
        this.results = results;
    }

    public boolean getIsHit() {
        return isHit;
    }

    public void setIsHit(boolean hit) {
        isHit = hit;
    }

    public void check(PointBean pointBean) {
        long startTime = System.nanoTime();

        if (Validator.validateX(pointBean.getX()) && Validator.validateY(pointBean.getY()) && Validator.validateR(pointBean.getR())){
            isHit = hit(pointBean.getX(), pointBean.getY(), pointBean.getR());
            PrimeFaces.current().executeScript("draw(" + pointBean.getX() + "," + pointBean.getY() + "," + pointBean.getR() + "," + isHit + ")");
            PointModel pointModel = new PointModel(pointBean.getX(), pointBean.getY(), pointBean.getR(), isHit, String.valueOf(System.nanoTime() - startTime), timeService.getCurrentTime());
            results.add(pointModel);
            pointService.save(pointModel);
        }
    }

    public static boolean hit(double x, double y, double r) {
        return inSquare(x, y, r) || inTriangle(x, y, r) || inCircle(x, y, r);
    }

    private static boolean inSquare(double x, double y, double r) {
        return x <= 0 && y >= 0 && -x <= r / 2 && y <= r;
    }

    private static boolean inTriangle(double x, double y, double r) {
        double[][] polygon = {
                {r, 0},
                {0, 0},
                {0, r / 2}
        };

        int n = polygon.length;
        boolean inside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = polygon[i][0], yi = polygon[i][1];
            double xj = polygon[j][0], yj = polygon[j][1];

            boolean intersect = ((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);
            if (intersect) {
                inside = !inside;
            }
        }

        return inside;
    }

    private static boolean inCircle(double x, double y, double r) {
        double centerX = 0;
        double centerY = 0;
        if (x <= centerX && y <= centerY && x >= (centerX - r) && y >= (centerY - r)) {
            double distanceSquared = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
            if (distanceSquared <= Math.pow(r, 2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
