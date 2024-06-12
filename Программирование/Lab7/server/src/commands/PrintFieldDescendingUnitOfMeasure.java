package commands;

import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import managers.DBManager;
import models.Product;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Команда 'print_field_descending_unit_of_measure'. Выводит значения поля unitOfMeasure всех элементов в порядке убывания.
 */
public class PrintFieldDescendingUnitOfMeasure extends Command {
    private final CollectionManager collectionManager;
    private final DBManager dbManager;

    public PrintFieldDescendingUnitOfMeasure(CollectionManager collectionManager, DBManager dbManager) {
        super("print_field_descending_unit_of_measure", "вывести значения поля unitOfMeasure всех элементов в порядке убывания");
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    /**
     * Выполнение команды.
     *
     * @param arguments Аргументы команды.
     * @return Результат выполнения команды (успешно/не успешно).
     */
    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        if (arguments.length > 1 && !arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        String collect = "Значения поля 'unitOfMeasure' всех элементов в порядке убывания:\n";
        collect += collectionManager.getCollection().stream()
                .map(Product::getUnitOfMeasure)
                .filter(unitOfMeasure -> unitOfMeasure != null)
                .sorted(Comparator.reverseOrder())
                .map(Object::toString).collect(Collectors.joining("\n"));
        collect += "\nСортировка выполнена успешно";
        return new ExecutionResponse(true, collect);
    }
}
