package commands;

import auxiliary.Console;
import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import models.Product;

import java.util.List;

/**
 * Команда 'remove_at_index'. Удаляет элемент из коллекции по индексу.
 */
public class RemoveAt extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveAt(Console console, CollectionManager collectionManager) {
        super("remove_at_index <index>", "удалить элемент, находящийся в заданной позиции коллекции (index)");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments.length < 2)
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        int index = -1;
        try {
            index = Integer.parseInt(arguments[0].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Индекс не распознан");
        }

        List<Product> collection = collectionManager.getCollection();

        if (index < 0 || index >= collection.size())
            return new ExecutionResponse(false, "Элемент на указанной позиции не существует");

        Product removedProduct = collection.remove(index);
        if (removedProduct != null) {
            return new ExecutionResponse(true, "Элемент успешно удален!");
        } else {
            return new ExecutionResponse(false, "Не удалось удалить элемент");
        }
    }
}
