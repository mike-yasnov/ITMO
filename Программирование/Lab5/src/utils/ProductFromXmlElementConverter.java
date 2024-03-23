package utils;

import org.w3c.dom.Element;
import subjects.Coordinates;
import subjects.Location;
import subjects.Person;
import subjects.Product;
import subjects.enums.Color;
import subjects.enums.Country;
import subjects.enums.UnitOfMeasure;

import java.time.ZonedDateTime;

public class ProductFromXmlElementConverter {
    public Product createProductFromElement(Element productElement) {
        Integer id = Integer.parseInt(productElement.getElementsByTagName("id").item(0).getTextContent());
        String name = productElement.getElementsByTagName("name").item(0).getTextContent();

        Element coordinatesElement = (Element) productElement.getElementsByTagName("coordinates").item(0);
        Integer x = Integer.parseInt(coordinatesElement.getElementsByTagName("x").item(0).getTextContent());
        int y = Integer.parseInt(coordinatesElement.getElementsByTagName("y").item(0).getTextContent());

        String creationDate = productElement.getElementsByTagName("creationDate").item(0).getTextContent();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(creationDate);

        Long price = Long.parseLong(productElement.getElementsByTagName("price").item(0).getTextContent());

        String partNumber = productElement.getElementsByTagName("partNumber").item(0).getTextContent();

        Integer manufactureCost = null;
        String manufactureCostValue = productElement.getElementsByTagName("manufactureCost").item(0).getTextContent();
        if (!manufactureCostValue.isEmpty()) {
            manufactureCost = Integer.parseInt(manufactureCostValue);
        }

        UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(productElement.getElementsByTagName("unitOfMeasure").item(0).getTextContent());

        Element ownerElement = (Element) productElement.getElementsByTagName("owner").item(0);

        String ownerName = null;
        String passportID = null;
        Color eyeColor = null;
        Color hairColor = null;
        Country nationality = null;
        Location location = null;

        // Проверяем, что ownerElement не является null
        if (ownerElement != null) {
            // Извлекаем данные о владельце из элемента XML
            ownerName = ownerElement.getElementsByTagName("name").item(0).getTextContent();
            passportID = ownerElement.getElementsByTagName("passportID").item(0).getTextContent();

            Element eyeColorElement = (Element) ownerElement.getElementsByTagName("eyeColor").item(0);
            eyeColor = eyeColorElement != null ? Color.valueOf(eyeColorElement.getTextContent()) : null;

            Element hairColorElement = (Element) ownerElement.getElementsByTagName("hairColor").item(0);
            hairColor = hairColorElement != null ? Color.valueOf(hairColorElement.getTextContent()) : null;

            Element nationalityElement = (Element) ownerElement.getElementsByTagName("nationality").item(0);
            nationality = nationalityElement != null ? Country.valueOf(nationalityElement.getTextContent()) : null;

            Element locationElement = (Element) ownerElement.getElementsByTagName("location").item(0);
            Long locationX = Long.parseLong(locationElement.getElementsByTagName("x").item(0).getTextContent());
            Integer locationY = Integer.parseInt(locationElement.getElementsByTagName("y").item(0).getTextContent());
            String locationName = locationElement.getElementsByTagName("name").item(0).getTextContent();

            location = new Location(locationX, locationY, locationName);
        }


        // Создаем объект models.Product с полученными данными
        Product product = new Product(name, new Coordinates(x, y), price, partNumber, manufactureCost, unitOfMeasure,
                new Person(ownerName, passportID, eyeColor, hairColor, nationality, location));
        product.setId(id);
        product.setCreationDate(zonedDateTime);

        return product;
    }
}

