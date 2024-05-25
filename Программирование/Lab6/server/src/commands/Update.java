package commands;


import auxiliary.Console;
import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import models.Product;


/**
 * Команда 'update'. Обновляет элемент коллекции.
 */
public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;


    public Update(Console console, CollectionManager collectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        Product newProduct = Product.getFromString(arguments[1]);
        long id = newProduct.getId();
        var old = collectionManager.getById((int) id);
        if (old == null || !collectionManager.getCollection().contains(old)) {
            return new ExecutionResponse(false, "Элемент с указанным ID не существует");
        }


        if (newProduct != null && newProduct.validate()) {
            collectionManager.removeFromCollection(old);
            collectionManager.addToCollection(newProduct);
            return new ExecutionResponse("Элемент успешно обновлен!");
        } else {
            return new ExecutionResponse(false, "Поля Продукта не валидны! Новый продукт не создан!");
        }

    }
}
