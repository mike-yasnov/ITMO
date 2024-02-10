import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import subjects.Coordinates;
import subjects.Location;
import subjects.Person;
import subjects.Product;
import subjects.enums.Color;
import subjects.enums.Country;
import subjects.enums.UnitOfMeasure;


import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Comparator;


/**
 * Класс, представляющий коллекцию продуктов.
 */
@SuppressWarnings("ALL")
public class ProductCollection {
    private final LinkedList<subjects.Product> products = new LinkedList<>();
    private ZonedDateTime initializationDate;

    /**
     * Выводит информацию о коллекции (тип, дата инициализации, количество элементов и т.д.).
     */
    public void info() {
        System.out.println("Тип коллекции: " + products.getClass().getSimpleName());
        if (initializationDate != null) {
            System.out.println("Дата инициализации: " + initializationDate.format(DateTimeFormatter.ISO_DATE_TIME));
        } else {
            System.out.println("Дата инициализации не указана");
        }
        System.out.println("Количество элементов: " + products.size());
    }

    /**
     * Добавляет новый продукт в коллекцию
     */
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Обновляет продукт по его id
     * @param productId id продукта
     * @param updatedProduct новый продукт
     */
    public void updateProduct(int productId, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(productId)) {
                products.set(i, updatedProduct);
                break;
            }
        }
    }

    /**
     *  Удалить элемент из коллекции по его id
     *  @param productId id продукта
     */
    public void removeProductById(int productId) {
        products.removeIf(product -> product.getId().equals(productId));
    }
    /**
     * Удаляет все элементы коллекции
     */
    public void clearCollection() {
        products.clear();
    }

    /**
     * Сохранение коллекции в файл
     * @param fileName путь к файлу
     */
    public void saveToFile(String fileName) {
        try {
            // Создаем новый XML файл
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            // Создаем фабрику для построения документов
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Создаем документ XML
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("products");
            doc.appendChild(rootElement);

            // Добавляем каждый продукт в документ XML
            for (Product product : products) {
                Element productElement = createProductElement(doc, product);
                rootElement.appendChild(productElement);
            }

            // Преобразуем документ XML в строку
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xmlString = writer.getBuffer().toString();

            // Записываем XML строку в файл
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(xmlString.getBytes());
            }

            System.out.println("Коллекция сохранена в файл: " + fileName);
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
        }
    }
    /**
     * Чтение коллекции из файла
     * @param fileName путь к файлу
     */
    public void loadFromFile(String fileName) {
        try {
            // Создаем считыватель для файла
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            // Считываем XML из файла
            StringBuilder xmlStringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                xmlStringBuilder.append(line);
            }
            String xmlString = xmlStringBuilder.toString();

            // Создаем фабрику для построения документов
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Парсим XML строку в документ
            ByteArrayInputStream input = new ByteArrayInputStream(xmlString.getBytes());
            Document doc = docBuilder.parse(input);

            // Получаем корневой элемент
            Element rootElement = doc.getDocumentElement();

            // Получаем список элементов <product>
            NodeList productNodes = rootElement.getElementsByTagName("product");

            // Очищаем текущую коллекцию перед загрузкой новых данных
            products.clear();

            // Проходим по каждому элементу <product> и создаем объект Product
            for (int i = 0; i < productNodes.getLength(); i++) {
                Element productElement = (Element) productNodes.item(i);
                Product product = createProductFromElement(productElement);
                products.add(product);
            }

            System.out.println("Коллекция загружена из файла: " + fileName);
        } catch (Exception e) {
            System.err.println("Ошибка при чтении из файла: " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Вспомогательный метод для чтения продукта из файла
     * @param document открытый файл
     * @param product продукт
     */
    private Element createProductElement(Document document, Product product) {
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

    /**
     * Вспомогательный метод для чтения продукта из файла
     * @param productElement прочитанный элемент
     */
    private Product createProductFromElement(Element productElement) {
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

    /**
     * Сортирует коллекцию в его естественном порядке
     */
    public void sortCollection() {
        products.sort(null);
    }
    /**
     * Отсортировать коллекцию в порядке, обратном нынешнему
     */
    public void reorderCollection() {
        Collections.reverse(products);
    }

    /**
     *  Удалить элемент, находящийся в заданной позиции коллекции (index)
     *  @param index index продукта
     */
    public void removeProductAt(int index) {
        if (index >= 0 && index < products.size()) {
            products.remove(index);
            System.out.println("Элемент успешно удален.");
        } else {
            System.out.println("Ошибка: некорректный индекс элемента.");
        }
    }
    /**
     * Вывести элементы коллекции в порядке возрастания
     */
    public void printAscending() {
        Collections.sort(products);
        for (Product product : products) {
            System.out.println(product);
        }
    }
    /**
     * Вывести значения поля unitOfMeasure всех элементов в порядке убывания
     */
    public void printFieldDescendingUnitOfMeasure() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                if (p1.getUnitOfMeasure() == null && p2.getUnitOfMeasure() == null) {
                    return 0;
                }
                if (p1.getUnitOfMeasure() == null) {
                    return 1;
                }
                if (p2.getUnitOfMeasure() == null) {
                    return -1;
                }
                return p2.getUnitOfMeasure().compareTo(p1.getUnitOfMeasure());
            }
        });

        for (Product product : products) {
            if (product.getUnitOfMeasure() != null) {
                System.out.println(product.getUnitOfMeasure());
            }
        }
    }
    /**
     * Вывести элементы, значение поля price которых равно заданному
     *  @param price цена продукта
     */
    public void filterByPrice(long price) {
        boolean found = false;
        for (Product product : products) {
            if (product.getPrice() == price) {
                System.out.println(product);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Нет продуктов с указанной ценой.");
        }
    }
    /**
     * Получает всю коллекцию
     */
    public LinkedList<Product> getProducts() {
        return products;
    }
}
