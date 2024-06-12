package models;

import auxiliary.Element;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Product extends Element {
    private int id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private Long price;
    private String partNumber;
    private Integer manufactureCost;
    private UnitOfMeasure unitOfMeasure;
    private Person owner;

    public Product(int id, String name, Coordinates coordinates, ZonedDateTime creationDate, Long price, String partNumber, int manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return ZonedDateTime.now();
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getPrice() {
        return price;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public int getManufactureCost() {
        return manufactureCost;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String dataString = "";
        dataString += id;
        dataString += "&#%";
        dataString += name;
        dataString += "&#%";
        dataString += coordinates.getX();
        dataString += "&#%";
        dataString += coordinates.getY();
        dataString += "&#%";
        dataString += formatter.format(creationDate);
        dataString += "&#%";
        dataString += price;
        dataString += "&#%";
        if(partNumber != null) dataString += partNumber.toString();
        else dataString += "null";
        dataString += "&#%";
        dataString += manufactureCost;
        dataString += "&#%";
        if(unitOfMeasure != null) dataString += unitOfMeasure.toString();
        else dataString += "null";
        dataString += "&#%";
        dataString += owner.getName();
        dataString += "&#%";
        dataString += owner.getPassportID();
        dataString += "&#%";
        dataString += owner.getEyeColor();
        dataString += "&#%";
        dataString += owner.getHairColor();
        dataString += "&#%";
        dataString += owner.getNationality();
        dataString += "&#%";
        dataString += owner.getLocation().getX();
        dataString += "&#%";
        dataString += owner.getLocation().getY();
        dataString += "&#%";
        dataString += owner.getLocation().getName();
        return dataString;
    }

    public static Product getFromString(String productString) {
        String[] parts = productString.split("&#%");
        if (parts.length != 17) {
            return null;
        }
        int id;
        String name;
        Coordinates coordinates;
        ZonedDateTime creationDate;
        Long price;
        String partNumber;
        int manufactureCost;
        UnitOfMeasure unitOfMeasure;
        Person owner;
        try {
            id = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            id = -1;
        }
        name = parts[1];
        try {
            coordinates = new Coordinates(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        } catch (NumberFormatException e) {
            coordinates = null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            creationDate = ZonedDateTime.parse(parts[4], formatter);
        } catch (DateTimeParseException e) {
            creationDate = null;
        }
        try {
            price = Long.parseLong(parts[5]);
        } catch (NumberFormatException e) {
            price = null;
        }
        partNumber = parts[6];
        try {
            manufactureCost = Integer.parseInt(parts[7]);
        } catch (NumberFormatException e) {
            manufactureCost = -1;
        }
        try {
            unitOfMeasure = UnitOfMeasure.valueOf(parts[8]);
        } catch (IllegalArgumentException e) {
            unitOfMeasure = null;
        }
        try {
            String personName = parts[9];
            String passportID = parts[10];
            EyeColor eyeColor = EyeColor.valueOf(parts[11]);
            HairColor hairColor = HairColor.valueOf(parts[12]);
            Country nationality = Country.valueOf(parts[13]);
            long x = Long.parseLong(parts[14]);
            Integer y = Integer.parseInt(parts[15]);
            String locationName = parts[16];
            Location location = new Location(x, y, locationName);
            owner = new Person(personName, passportID, eyeColor, hairColor, nationality, location);
        } catch (IllegalArgumentException e) {
            owner = null;
        }
        return new Product(id, name, coordinates, creationDate, price, partNumber, manufactureCost, unitOfMeasure, owner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Float.compare(product.manufactureCost, manufactureCost) == 0 && Objects.equals(name, product.name) && Objects.equals(coordinates, product.coordinates) && Objects.equals(creationDate, product.creationDate) && Objects.equals(price, product.price) && Objects.equals(partNumber, product.partNumber) && unitOfMeasure == product.unitOfMeasure && Objects.equals(owner, product.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, partNumber, manufactureCost, unitOfMeasure, owner);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", partNumber='" + partNumber + '\'' +
                ", manufactureCost=" + manufactureCost +
                ", unitOfMeasure=" + unitOfMeasure +
                ", owner=" + owner +
                '}';
    }

    @Override
    public int compareTo(Element element) {
        return (int) (this.id - element.getId());
    }

    @Override
    public boolean validate() {
//        if (id <= 0) return false;
//        if (name == null || name.isEmpty()) return false;
//        if (creationDate == null) return false;
//        if (coordinates == null) return false;
//        if (price == null || price <= 0) return false;
//        if (partNumber != null & !partNumber.equals("null")) {
//            return (partNumber.length() >= 27 && partNumber.length() <= 42);
//        } else {
            return true;
        }
    }

