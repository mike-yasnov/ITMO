package subjects;

/**
 * Класс, представляющий координаты.
 */
public class Coordinates {
    private Integer x;
    private int y;

    /**
     * Создает новый экземпляр координат.
     *
     * @param x координата x
     * @param y координата y
     */
    public Coordinates(Integer x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" +
                "x=" + x +
                ", y=" + y +
                ')';
    }

}
