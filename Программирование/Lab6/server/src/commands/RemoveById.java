package commands;

import auxiliary.Console;
import auxiliary.ExecutionResponse;
import managers.CollectionManager;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 */
public class RemoveById extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveById(Console console, CollectionManager collectionManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
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

        long id = -1;
        try {
            id = Long.parseLong(arguments[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "ID не распознан");
        }

        if (collectionManager.getById((int) id) == null || !collectionManager.getCollection().contains(collectionManager.getById((int) id)))
            return new ExecutionResponse(false, "Элемент с указанным ID не существует");

        collectionManager.removeFromCollection(collectionManager.getById((int) id));
        return new ExecutionResponse("Элемент успешно удален!");
    }
}