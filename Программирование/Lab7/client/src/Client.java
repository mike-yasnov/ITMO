import auxiliary.Runner;
import auxiliary.StandardConsole;
import commands.CommandTypes;
import managers.NetworkManager;

import java.util.HashMap;
import java.util.Map;

public class Client {
    public static void main(String[] args) {
        var console = new StandardConsole();
        NetworkManager networkManager = new NetworkManager(8000);

        while (!networkManager.init(args)) {}
        Map<CommandTypes,String[]> commands = new HashMap<>();
        commands.put(CommandTypes.HELP, new String[]{"help", "вывести справку по доступным командам"});
        commands.put(CommandTypes.ADD,new String[]{"add", "добавить новый элемент в коллекцию"});
        commands.put(CommandTypes.REORDER, new String[]{"reorder", "отсортировать коллекцию в порядке, обратном нынешнему новый элемент в коллекцию, если его значение меньше значения наименьшего элемента этой коллекции"});
        commands.put(CommandTypes.CLEAR,new String[]{"clear", "очистить коллекцию"});
        commands.put(CommandTypes.FILTER_BY_PRICE, new String[]{"filter_by_price <price>", "вывести элементы, значение поля price которых равно заданному"});
        commands.put(CommandTypes.HISTORY, new String[]{"history", "Выводит последние 13 команд без аргументов"});
        commands.put(CommandTypes.INFO, new String[]{"info", "вывести информацию о коллекции"});
        commands.put(CommandTypes.SORT, new String[]{"sort", "отсортировать коллекцию в естественном порядке"});
        commands.put(CommandTypes.PRINT_FIELD_DESCENDING_UNIT_OF_MEASURE, new String[]{"print_field_descending_unit_of_measure", "вывести значения поля unitOfMeasure всех элементов в порядке убывания"});
        commands.put(CommandTypes.REMOVE_BY_ID,new String[]{"remove_by_id <ID>", "удалить элемент из коллекции по ID"});
        commands.put(CommandTypes.REMOVE_AT,new String[]{"remove_at {index}", "удалить элемент, находящийся в заданной позиции коллекции (index)"});
        commands.put(CommandTypes.SHOW, new String[]{"show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"});
        commands.put(CommandTypes.UPDATE, new String[]{"update <ID> {element}", "обновить значение элемента коллекции по ID"});
        commands.put(CommandTypes.EXIT,new String[]{"exit", "завершить программу (без сохранения в файл)"});
        commands.put(CommandTypes.EXECUTE_SCRIPT,new String[]{"execute_script <file_name>", "исполнить скрипт из указанного файла"});
        commands.put(CommandTypes.PRINT_ASCENDING, new String[]{"print_ascending", "выводит элементы по возрастанию"});
        new Runner(networkManager,console, commands).interactiveMode();

    }
}
