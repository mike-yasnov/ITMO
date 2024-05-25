package commands;

import auxiliary.Console;
import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import models.Product;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'reorder'. Сортирует коллекцию в порядке, обратном нынешнему.
 */
public class Reorder extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Reorder(Console console, CollectionManager collectionManager) {
        super("reorder", "отсортировать коллекцию в порядке, обратном нынешнему");
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
        if (!arguments[1].isEmpty()) {
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
