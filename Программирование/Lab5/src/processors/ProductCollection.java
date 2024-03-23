package processors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import subjects.Product;
import utils.ProductFromXmlElementConverter;
import utils.ProductToXmlElementConverter;
import utils.XmlFileReader;
import utils.XmlFileWriter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Comparator;



/**
 * Класс, представляющий коллекцию продуктов.
 */
@SuppressWarnings("ALL")
public class ProductCollection {
    private final LinkedList<subjects.Product> products = new LinkedList<>();
    private ZonedDateTime initializationDate;
    private final XmlFileReader xmlFileReader = new XmlFileReader();
    private final XmlFileWriter xmlFileWriter = new XmlFileWriter();
    private final ProductFromXmlElementConverter productFromXmlElementConverter = new ProductFromXmlElementConverter();
    private final ProductToXmlElementConverter productToXmlElementConverter = new ProductToXmlElementConverter();



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
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("products");
            document.appendChild(rootElement);

            for (Product product : products) {
                Element productElement = productToXmlElementConverter.createProductElement(document, product);
                rootElement.appendChild(productElement);
            }

            xmlFileWriter.writeXmlFile(document, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Чтение коллекции из файла
     * @param fileName путь к файлу
     */
    public void loadFromFile(String fileName) {
        try {
            Document document = xmlFileReader.readXmlFile(fileName);
            if (document != null) {
                NodeList nodeList = document.getElementsByTagName("product");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    Product product = productFromXmlElementConverter.createProductFromElement(element);
                    products.add(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
