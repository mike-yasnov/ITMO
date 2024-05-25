package commands;

import auxiliary.Console;
import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import models.Product;

import java.util.Iterator;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
        commandType=CommandTypes.CLEAR;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        Iterator<Product> iterator = collectionManager.getCollection().iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            iterator.remove();
            collectionManager.removeFromCollection(p);
        }
        return new ExecutionResponse("Коллекция очищена!");
    }
}
