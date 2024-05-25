package commands;

import auxiliary.Console;
import auxiliary.ExecutionResponse;
import managers.CollectionManager;
import models.Product;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Команда 'print_field_descending_unit_of_measure'. Выводит значения поля unitOfMeasure всех элементов в порядке убывания.
 */
public class PrintFieldDescendingUnitOfMeasure extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintFieldDescendingUnitOfMeasure(Console console, CollectionManager collectionManager) {
        super("print_field_descending_unit_of_measure", "вывести значения поля unitOfMeasure всех элементов в порядке убывания");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполнение команды.
     *
     * @param arguments Аргументы команды.
     * @return Результат выполнения команды (успешно/не успешно).
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        StringBuilder res = new StringBuilder();

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


