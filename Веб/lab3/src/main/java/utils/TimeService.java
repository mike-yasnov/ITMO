package utils;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ManagedBean(name = "timeService")
@ApplicationScoped
public class TimeService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public String getCurrentTime() {
        return formatter.format(LocalDateTime.now());
    }
    
    public static String formatTime(LocalDateTime time) {
        return formatter.format(time);
    }
}
