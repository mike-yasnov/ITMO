import auxiliary.Console;
import auxiliary.ExecutionResponse;
import auxiliary.StandardConsole;
import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.NetworkManager;
import managers.SaveManager;

import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    static String[] userCommand = new String[2];
    static byte[] arr = new byte[5069];
    static int len = arr.length;

    public static void main(String[] args) {
        var console = new StandardConsole();

        var saveManager = new SaveManager(console);
        var collectionManager = new CollectionManager(saveManager);
        if (!collectionManager.loadCollection()) {
            logger.severe("Ошибка загрузки коллекции. Программа завершает работу.");
            System.exit(1);
        }
        var networkManager = new NetworkManager(5000, 800);
        while (!networkManager.init()) {
            logger.info("Менеджер сетевого взаимодействия инициализирован!");
        }
        var commandManager = new CommandManager() {{
            register("info", new Info(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("remove_at", new RemoveAt(console, collectionManager));
            register("reorder", new Reorder(console, collectionManager));
            register("sort", new Sort(console, collectionManager));
            register("print_field_descending_unit_of_measure", new PrintFieldDescendingUnitOfMeasure(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("print_ascending", new PrintAscending(console, collectionManager));
            register("remove_by_id", new RemoveById(console, collectionManager));
            register("filter_by_price", new FilterByPrice(console, collectionManager));
        }};
        run(networkManager, console, commandManager);
    }

    public static void run(NetworkManager networkManager, Console console, CommandManager commandManager) {
        while (true) {
            arr = networkManager.receiveData(len);
            Container commandd = NetworkManager.deserialize(arr);
            if (commandd != null) {
                userCommand[0] = commandd.getCommandType().Type();
                userCommand[1] = commandd.getArgs();
                var command = commandManager.getCommands().get(userCommand[0]);
                ExecutionResponse response;
                if (userCommand[0].isEmpty()) response = new ExecutionResponse("");
                if (command == null)
                    response = new ExecutionResponse(false, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");
                else {
                    response = command.apply(userCommand);
                }
                logger.info("Команда обработана!");
                networkManager.sendData(NetworkManager.serializer(response));
                logger.info("Отправлен ответ клиенту!");
            }
        }
    }
}


