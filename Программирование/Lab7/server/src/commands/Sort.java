package commands;

import auxiliary.Console;
import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import models.Product;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'sort'. Сортирует коллекцию в естественном порядке.
 */
public class Sort extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Sort(Console console, CollectionManager collectionManager) {
        super("sort", "отсортировать коллекцию в естественном порядке");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        if (!arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        List<Product> products = collectionManager.getCollection().stream()
                .collect(Collectors.toList());
        Collections.sort(products);
        collectionManager.getCollection().clear();
        collectionManager.getCollection().addAll(products);

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("Коллекция успешно отсортирована в естественном порядке:\n");
        products.forEach(product -> responseBuilder.append(product).append("\n"));
        return new ExecutionResponse(true, responseBuilder.toString().trim());
    }
}
