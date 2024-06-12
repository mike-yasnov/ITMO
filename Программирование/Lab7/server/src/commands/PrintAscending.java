package commands;

import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import managers.DBManager;
import models.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'print_ascending'. Выводит элементы коллекции в порядке возрастания.
 */
public class PrintAscending extends Command {
    private final CollectionManager collectionManager;
    private final DBManager dbManager;

    public PrintAscending(CollectionManager collectionManager, DBManager dbManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
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

        List<Product> sortedProducts = collectionManager.getCollection().stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        if (sortedProducts.isEmpty()) {
            return new ExecutionResponse(false, "Коллекция пуста.");
        } else {
            StringBuilder responseBuilder = new StringBuilder();
            responseBuilder.append("Элементы коллекции в порядке возрастания:\n");
            sortedProducts.forEach(product -> responseBuilder.append(product).append("\n"));
            return new ExecutionResponse(true, responseBuilder.toString().trim());
        }
    }
}
