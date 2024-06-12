package commands;

import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import managers.DBManager;
import models.Product;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'filter_by_price'. Выводит элементы, значение поля price которых равно заданному.
 */
public class FilterByPrice extends Command {
    private final CollectionManager collectionManager;

    public FilterByPrice(CollectionManager collectionManager) {
        super("filter_by_price <PRICE>", "вывести элементы, значение поля price которых равно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        if (arguments.length < 2) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        long price;
        try {
            price = Long.parseLong(arguments[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Цена не распознана");
        }

        List<Product> filteredProducts = filterByPrice(price);

        if (filteredProducts.isEmpty()) {
            return new ExecutionResponse(false, "Продуктов с ценой " + price + " не обнаружено.");
        } else {
            StringBuilder responseBuilder = new StringBuilder();
            responseBuilder.append("Продукты с ценой ").append(price).append(":\n");
            filteredProducts.forEach(product -> responseBuilder.append(product).append("\n"));
            return new ExecutionResponse(true, responseBuilder.toString().trim());
        }
    }

    private List<Product> filterByPrice(long price) {
        return collectionManager.getCollection().stream()
                .filter(product -> product.getPrice() == price)
                .collect(Collectors.toList());
    }
}
