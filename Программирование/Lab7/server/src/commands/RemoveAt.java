package commands;

import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import managers.DBManager;
import models.Product;

import java.util.List;

/**
 * Команда 'remove_at_index'. Удаляет элемент из коллекции по индексу.
 */
public class RemoveAt extends Command {
    private final CollectionManager collectionManager;
    private final DBManager dbManager;

    public RemoveAt(CollectionManager collectionManager, DBManager dbManager) {
        super("remove_at_index <index>", "удалить элемент, находящийся в заданной позиции коллекции (index)");
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        if (arguments.length < 2 || arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        int index;
        try {
            index = Integer.parseInt(arguments[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Индекс не распознан");
        }

        List<Product> collection = collectionManager.getCollection().stream().toList();

        if (index < 0 || index >= collection.size()) {
            return new ExecutionResponse(false, "Элемент на указанной позиции не существует");
        }

        Product removedProduct = collection.get(index);
        if (removedProduct != null) {
            collectionManager.removeFromCollection(removedProduct);
            if (!dbManager.deleteById(removedProduct.getId())) {
                return new ExecutionResponse(false, "Не удалось удалить элемент из базы данных");
            }
            return new ExecutionResponse(true, "Элемент успешно удален!");
        } else {
            return new ExecutionResponse(false, "Не удалось удалить элемент");
        }
    }
}
