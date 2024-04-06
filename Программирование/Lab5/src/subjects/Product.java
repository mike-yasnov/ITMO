package subjects;

import subjects.enums.UnitOfMeasure;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.HashSet;

/**
 * Класс, представляющий продукт.
 */

public class Product implements Comparable<Product> {
    private Integer id;
    private static int lastId = 0;
    private final String name;
    private final Coordinates coordinates;
    private ZonedDateTime creationDate;
    private final Long price;
    private String partNumber;
    private Integer manufactureCost;
    private UnitOfMeasure unitOfMeasure;
    private Person owner;
    private static final Set<String> usedPartNumbers = new HashSet<>();
    /**
     * Конструктор для создания экземпляра продукта.
     *
     * @param name             название продукта
     * @param coordinates      координаты продукта
     * @param price            цена продукта
     * @param partNumber       номер детали продукта (должен быть уникальным и иметь длину не менее 16 символов)
     * @param manufactureCost  стоимость производства продукта
     * @param unitOfMeasure    единицы измерения продукта
     * @param owner            владелец продукта
     */
    public Product(String name, Coordinates coordinates, Long price, String partNumber,
                   Integer manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.price = price;
        checkPartNumber(partNumber);
        this.partNumber = partNumber;
        usedPartNumbers.add(partNumber);

        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
        this.id = ++lastId;
    }

    private void checkPartNumber(String partNumber) {
        if (usedPartNumbers.contains(partNumber) || partNumber.length() < 16) {
            throw new IllegalArgumentException("Номер детали должен быть уникальным и содержать не менее 16 символов: " + partNumber);
        }
    }
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Long getPrice() {
        return price;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        checkPartNumber(partNumber);
        this.partNumber = partNumber;
    }

    public Integer getManufactureCost() {
        return manufactureCost;
    }

    public void setManufactureCost(Integer manufactureCost) {
        this.manufactureCost = manufactureCost;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public void setCreationDate(ZonedDateTime datetime) {
        this.creationDate = datetime;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Продукт: (id=" + id +
                ", name='" + name + '\'' +
                "\n coordinates=" + coordinates +
                "\n creationDate=" + creationDate +
                "\n price=" + price +
                "\n partNumber='" + partNumber + '\'' +
                "\n manufactureCost=" + manufactureCost +
                "\n unitOfMeasure=" + unitOfMeasure +
                "\n owner=" + owner +
                ')';
    }
    @Override
    public int compareTo(Product otherProduct) {
        return this.id.compareTo(otherProduct.id);
    }
}
