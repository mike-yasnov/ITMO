package recource;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Result implements Serializable {
    private String x;
    private String y;
    private String r;
    private boolean isHit;
    private double executionTime;
    private String serverTime;

    public Result(String x, String y, String r, boolean isHit, double time, String serverTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isHit = isHit;
        this.executionTime = time;
        this.serverTime = serverTime;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public boolean getIsHit() {
        return isHit;
    }

    public void setIsHit(boolean hit) {
        isHit = hit;
    }

    public double getTime() {
        return executionTime;
    }

    public void setTime(double time) {
        this.executionTime = time;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r, isHit);
    }

    @Override
    public String toString() {
        return "Result{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", r='" + r + '\'' +
                ", isHit=" + isHit +
                ", executionTime=" + executionTime +
                ", serverTime='" + serverTime + '\'' +
                '}';
    }
}
