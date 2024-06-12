package commands;

import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import managers.DBManager;
import models.Product;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 */
public class Update extends Command {
    private final CollectionManager collectionManager;
    private final DBManager dbManager;

    public Update(CollectionManager collectionManager, DBManager dbManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        if (arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        Product newProduct = Product.getFromString(arguments[1]);
        long id = newProduct.getId();
        var old = collectionManager.getById((int) id);

        if (old == null || !collectionManager.getCollection().contains(old)) {
            return new ExecutionResponse(false, "Элемент с указанным ID не существует");
        }

        if (newProduct != null && newProduct.validate()) {
            collectionManager.removeFromCollection(old);
            collectionManager.addToCollection(newProduct);
            if (!dbManager.update((int) id, newProduct)) {
                return new ExecutionResponse(false, "Ошибка при обновлении продукта в базе данных!");
            }
            return new ExecutionResponse("Элемент успешно обновлен!");
        } else {
            return new ExecutionResponse(false, "Поля Продукта не валидны! Новый продукт не создан!");
        }
    }
}
