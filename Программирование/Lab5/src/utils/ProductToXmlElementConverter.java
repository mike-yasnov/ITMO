package utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import subjects.Product;

public class ProductToXmlElementConverter {
    public Element createProductElement(Document document, Product product) {
        // Создаем элемент продукта и добавляем в него все необходимые элементы и атрибуты
        Element productElement = document.createElement("product");

        // Добавляем элемент id
        Element idElement = document.createElement("id");
        idElement.appendChild(document.createTextNode(product.getId().toString()));
        productElement.appendChild(idElement);

        // Добавляем элемент name
        Element nameElement = document.createElement("name");
        nameElement.appendChild(document.createTextNode(product.getName()));
        productElement.appendChild(nameElement);

        // Добавляем элемент coordinates
        Element coordinatesElement = document.createElement("coordinates");
        Element xElement = document.createElement("x");
        xElement.appendChild(document.createTextNode(product.getCoordinates().getX().toString()));
        coordinatesElement.appendChild(xElement);
        Element yElement = document.createElement("y");
        yElement.appendChild(document.createTextNode(String.valueOf(product.getCoordinates().getY())));
        coordinatesElement.appendChild(yElement);
        productElement.appendChild(coordinatesElement);

        // Добавляем элемент creationDate
        Element creationDateElement = document.createElement("creationDate");
        creationDateElement.appendChild(document.createTextNode(product.getCreationDate().toString()));
        productElement.appendChild(creationDateElement);

        // Добавляем элемент price
        Element priceElement = document.createElement("price");
        priceElement.appendChild(document.createTextNode(product.getPrice().toString()));
        productElement.appendChild(priceElement);

        // Добавляем элемент partNumber
        Element partNumberElement = document.createElement("partNumber");
        partNumberElement.appendChild(document.createTextNode(product.getPartNumber()));
        productElement.appendChild(partNumberElement);

        // Добавляем элемент manufactureCost
        Element manufactureCostElement = document.createElement("manufactureCost");
        if (product.getManufactureCost() != null) {
            manufactureCostElement.appendChild(document.createTextNode(product.getManufactureCost().toString()));
        }
        productElement.appendChild(manufactureCostElement);

        // Добавляем элемент unitOfMeasure
        Element unitOfMeasureElement = document.createElement("unitOfMeasure");
        unitOfMeasureElement.appendChild(document.createTextNode(product.getUnitOfMeasure().name()));
        productElement.appendChild(unitOfMeasureElement);

        // Добавляем элемент owner
        Element ownerElement = document.createElement("owner");

        // Добавляем элемент name владельца
        Element ownerNameElement = document.createElement("name");
        ownerNameElement.appendChild(document.createTextNode(product.getOwner().getName()));
        ownerElement.appendChild(ownerNameElement);

        // Добавляем элемент passportID владельца
        Element passportIDElement = document.createElement("passportID");
        passportIDElement.appendChild(document.createTextNode(product.getOwner().getPassportID()));
        ownerElement.appendChild(passportIDElement);

        // Добавляем элемент eyeColor владельца (если не null)
        if (product.getOwner().getEyeColor() != null) {
            Element eyeColorElement = document.createElement("eyeColor");
            eyeColorElement.appendChild(document.createTextNode(product.getOwner().getEyeColor().name()));
            ownerElement.appendChild(eyeColorElement);
        }

        // Добавляем элемент hairColor владельца (если не null)
        if (product.getOwner().getHairColor() != null) {
            Element hairColorElement = document.createElement("hairColor");
            hairColorElement.appendChild(document.createTextNode(product.getOwner().getHairColor().name()));
            ownerElement.appendChild(hairColorElement);
        }

        // Добавляем элемент nationality владельца (если не null)
        if (product.getOwner().getNationality() != null) {
            Element nationalityElement = document.createElement("nationality");
            nationalityElement.appendChild(document.createTextNode(product.getOwner().getNationality().name()));
            ownerElement.appendChild(nationalityElement);
        }

        // Добавляем элемент location владельца (если не null)
        if (product.getOwner().getLocation() != null) {
            Element locationElement = document.createElement("location");

            // Добавляем элемент x в location
            Element locationXElement = document.createElement("x");
            locationXElement.appendChild(document.createTextNode(product.getOwner().getLocation().getX().toString()));
            locationElement.appendChild(locationXElement);

            // Добавляем элемент y в location
            Element locationYElement = document.createElement("y");
            locationYElement.appendChild(document.createTextNode(product.getOwner().getLocation().getY().toString()));
            locationElement.appendChild(locationYElement);

            // Добавляем элемент name в location (если не null)
            if (product.getOwner().getLocation().getName() != null) {
                Element locationNameElement = document.createElement("name");
                locationNameElement.appendChild(document.createTextNode(product.getOwner().getLocation().getName()));
                locationElement.appendChild(locationNameElement);
            }

            ownerElement.appendChild(locationElement);
        }
        productElement.appendChild(ownerElement);
        return productElement;
    }
}

