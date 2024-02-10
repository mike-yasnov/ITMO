package subjects;

/**
 * Класс, представляющий местоположение.
 */
public class Location {
    private Long x; // Поле не может быть null
    private Integer y; // Поле не может быть null
    private String name; // Поле может быть null

    /**
     * Создает новый экземпляр местоположения.
     *
     * @param x    координата x местоположения
     * @param y    координата y местоположения
     * @param name название местоположения
     */
    public Location(Long x, Integer y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "models.Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
