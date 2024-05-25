package commands;

import auxiliary.Console;
import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import models.Product;

import java.time.ZonedDateTime;


/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
        commandType = CommandTypes.ADD;
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
        Product p = Product.getFromString(arguments[1]);
        p.setId(collectionManager.getFreeId());
        if (p != null && p.validate()) {
            collectionManager.addToCollection(p);
            return new ExecutionResponse("Продукт успешно добавлено!");
        } else return new ExecutionResponse(false, "Поля Продукта не валидны! Продукт не создан!");

    }
}