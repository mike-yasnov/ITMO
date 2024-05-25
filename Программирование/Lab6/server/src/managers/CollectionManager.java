package managers;

import models.Product;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Менеджер коллекции, отвечающий за управление коллекцией продуктов.
 */
public class CollectionManager {

    private LinkedList<Product> collection = new LinkedList<>();

    private final AtomicInteger idCounter = new AtomicInteger(0);

    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final SaveManager saveManager;

    /**
     * Конструктор класса CollectionManager.
     * @param saveManager менеджер сохранения и загрузки коллекции
     */
    public CollectionManager(SaveManager saveManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.saveManager = saveManager;
    }

    public void setIdCounter(int lastId) {
        idCounter.set(lastId);
    }

    /**
     * Получить следующий свободный идентификатор.
     * @return следующий свободный идентификатор
     */
    public int getFreeId() {
        return idCounter.incrementAndGet();
    }

    /**
     * Получить коллекцию продуктов.
     * @return коллекция продуктов
     */
    public LinkedList<Product> getCollection() {
        return collection;
    }

    /**
     * Получить время последней инициализации коллекции.
     * @return время последней инициализации коллекции
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Получить время последнего сохранения коллекции.
     * @return время последнего сохранения коллекции
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Получить тип коллекции.
     * @return тип коллекции
     */
    public String collectionType() {
        return collection.getClass().getName();
    }

    /**
     * Получить размер коллекции.
     * @return размер коллекции
     */
    public int collectionSize() {
        return collection.size();
    }

    /**
     * Получить первый элемент коллекции.
     * @return первый элемент коллекции
     */
    public Product getFirst() {
        return collection.getFirst();
    }

    /**
     * Получить продукт по его идентификатору.
     * @param id идентификатор продукта
     * @return продукт с указанным идентификатором или null, если такого продукта нет в коллекции
     */
    public Product getById(int id) {
        return collection.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Проверить наличие продукта в коллекции по его идентификатору.
     * @param id идентификатор продукта
     * @return true, если продукт с указанным идентификатором существует в коллекции, в противном случае - false
     */
    public boolean checkExist(int id) {
        return collection.stream()
                .anyMatch(product -> product.getId() == id);
    }

    /**
     * Получить продукт из коллекции по его значению.
     * @param elementToFind объект продукта для поиска
     * @return найденный продукт или null, если продукт не найден
     */
    public Product getByValue(Product elementToFind) {
        return collection.stream()
                .filter(element -> element.equals(elementToFind))
                .findFirst()
                .orElse(null);
    }

    /**
     * Добавить продукт в коллекцию.
     * @param element добавляемый продукт
     */
    public void addToCollection(Product element) {
        collection.add(element);
    }

    /**
     * Удалить продукт из коллекции.
     * @param element удаляемый продукт
     */
    public void removeFromCollection(Product element) {
        collection.remove(element);
    }

    /**
     * Очистить коллекцию.
     */
    public void clearCollection() {
        collection.clear();
    }

    /**
     * Сохранить коллекцию.
     */
    public void saveCollection() {
        saveManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Загрузить коллекцию.
     * @return true, если коллекция успешно загружена, в противном случае - false
     */
    public boolean loadCollection() {
        collection = new LinkedList<>(saveManager.readCollection(collection));
        lastInitTime = LocalDateTime.now();
        if (!collection.isEmpty()) {
            setIdCounter(collection.getLast().getId());
        }
        return true;
    }

    /**
     * Представить коллекцию в виде строки.
     * @return строковое представление коллекции
     */
    @Override
    public String toString() {
        if (collection.isEmpty()) {
            return "Коллекция пуста!";
        }
        StringBuilder info = new StringBuilder();
        for (Product product : collection) {
            info.append(product).append("\n");
        }
        return info.toString();
    }
}
