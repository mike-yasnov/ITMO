import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ManagedBean(name = "timeBean")
@ApplicationScoped
public class TimeBean {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy г. HH:mm:ss");

    public String getCurrentTime() {
        return LocalDateTime.now().format(formatter);
    }
}
