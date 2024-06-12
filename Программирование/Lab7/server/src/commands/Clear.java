package commands;

import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import managers.DBManager;
import models.Product;

import java.util.Iterator;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final CollectionManager collectionManager;
    private final DBManager dbManager;

    public Clear(CollectionManager collectionManager, DBManager dbManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        Iterator<Product> iterator = collectionManager.getCollection().iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            iterator.remove();
            collectionManager.removeFromCollection(p);
        }
        dbManager.clear(login); // Добавление метода для очистки базы данных, если требуется
        return new ExecutionResponse("Коллекция очищена!");
    }
}
