package commands;

import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import managers.DBManager;
import models.Product;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'reorder'. Сортирует коллекцию в порядке, обратном нынешнему.
 */
public class Reorder extends Command {
    private final CollectionManager collectionManager;
    private final DBManager dbManager;

    public Reorder(CollectionManager collectionManager, DBManager dbManager) {
        super("reorder", "отсортировать коллекцию в порядке, обратном нынешнему");
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
        if (arguments.length > 1 && !arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        List<Product> products = collectionManager.getCollection().stream()
                .collect(Collectors.toList());
        Collections.reverse(products);
        collectionManager.getCollection().clear();
        collectionManager.getCollection().addAll(products);

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("Коллекция успешно отсортирована в обратном порядке:\n");
        products.forEach(product -> responseBuilder.append(product).append("\n"));
        return new ExecutionResponse(true, responseBuilder.toString().trim());
    }
}
