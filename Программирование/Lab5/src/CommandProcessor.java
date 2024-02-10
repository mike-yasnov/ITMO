import subjects.Coordinates;
import subjects.Location;
import subjects.Person;
import subjects.Product;
import subjects.enums.Color;
import subjects.enums.Country;
import subjects.enums.UnitOfMeasure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Класс для обработки команд пользователя.
 * Поддерживаемые команды:
 * - help: выводит справку по доступным командам
 * - info: выводит информацию о коллекции
 * - show: выводит все элементы коллекции
 * - add {element}: добавляет новый элемент в коллекцию
 * - update id {element}: обновляет значение элемента коллекции с указанным id
 * - remove_by_id id: удаляет элемент из коллекции по указанному id
 * - clear: очищает коллекцию
 * - save: сохраняет коллекцию в файл
 * - execute_script file_name: считывает и исполняет скрипт из указанного файла
 * - exit: завершает программу без сохранения изменений в файл
 * - remove_at index: удаляет элемент из коллекции по указанному индексу
 * - reorder: сортирует коллекцию в обратном порядке
 * - sort: сортирует коллекцию в естественном порядке
 * - filter_by_price price: выводит элементы коллекции с указанной ценой
 * - print_ascending: выводит элементы коллекции в порядке возрастания
 * - print_field_descending_unit_of_measure: выводит значения поля unitOfMeasure всех элементов в порядке убывания
 */
@SuppressWarnings("ALL")
public class CommandProcessor {
    private final ProductCollection productCollection;
    private Scanner scanner;
    private final String fileName;

    public CommandProcessor(ProductCollection productCollection, Scanner scanner, String fileName) {
        this.productCollection = productCollection;
        this.scanner = scanner;
        this.fileName = fileName;
    }

    /**
     * Запускает обработку команд.
     */

    public void run() {
        while (true) {
            System.out.print("Введите команду (help для справки): ");
            String input = scanner.nextLine().trim();
            processCommand(input);
        }
    }

    /**
     * Обрабатывает введенную пользователем команду.
     * @param command введенная пользователем команда
     */
    private void processCommand(String input) {
        String[] commandParts = input.split(" ", 2);
        String command = commandParts[0].toLowerCase();

        switch (command) {
            case "help":
                displayHelp();
                break;
            case "info":
                info();
                break;
            case "add":
                addProduct();
                break;
            case "update":
                updateProduct(commandParts);
                break;
            case "remove_by_id":
                removeProductById(commandParts);
                break;
            case "clear":
                clearCollection();
                break;
            case "save":
                saveCollectionToFile();
                break;
            case "show":
                showCollection();
                break;
            case "sort":
                sortCollection();
                break;
            case "reorder":
                reorderCollection();
                break;
            case "remove_at":
                removeProductAt(commandParts);
                break;
            case "print_ascending":
                printAscending();
                break;
            case "print_field_descending_unit_of_measure":
                printFieldDescendingUnitOfMeasure();
                break;
            case "filter_by_price":
                filterByPrice(commandParts);
                break;
            case "execute_script":
                executeScript(commandParts[1]);
                break;
            case "exit":
                exit();
                break;
            default:
                System.out.println("Некорректная команда. Введите 'help' для справки.");
        }
    }

    /**
     * Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
     * @param fileName путь к файлу со скриптом
     */
    public void executeScript(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("add")) {
                    // Считываем данные о продукте из файла и передаем их в метод addProduct()
                    String productName = reader.readLine();
                    int productX = Integer.parseInt(reader.readLine());
                    int productY = Integer.parseInt(reader.readLine());
                    long productPrice = Long.parseLong(reader.readLine());
                    String productPartNumber = reader.readLine();
                    Integer productManufactureCost = Integer.parseInt(reader.readLine());
                    UnitOfMeasure productUnitOfMeasure = UnitOfMeasure.valueOf(reader.readLine());
                    String personName = reader.readLine();
                    String personPassportId = reader.readLine();
                    Color personEyeColor = Color.valueOf(reader.readLine());
                    Color personHairColor = Color.valueOf(reader.readLine());
                    Country personNationality = Country.valueOf(reader.readLine());

                    Long locationX = Long.parseLong(reader.readLine());
                    Integer locationY = Integer.parseInt(reader.readLine());
                    String locationName = reader.readLine();

                    Location personLocation = new Location(locationX, locationY, locationName);

                    // Создаем экземпляр продукта и добавляем его в коллекцию
                    Product product = new Product(productName, new Coordinates(productX, productY), productPrice, productPartNumber, productManufactureCost, productUnitOfMeasure,
                            new Person(personName, personPassportId, personEyeColor, personHairColor, personNationality, personLocation));
                    productCollection.addProduct(product);
                } else {
                    // Передаем другие команды в обработку
                    processCommand(line);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при выполнении скрипта: " + e.getMessage());
        }
    }
    /**
     * Выводит справку по доступным командам.
     */
    private void displayHelp() {
        System.out.println("Доступные команды:");
        System.out.println("help : вывести справку по доступным командам");
        System.out.println("info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");

        System.out.println("add : добавить новый элемент в коллекцию");
        System.out.println("update {id} : обновить значение элемента коллекции по id");
        System.out.println("remove_by_id {id} : удаляет элемент коллекции по его id");
        System.out.println("remove_at {index} : удалить элемент, находящийся в заданной позиции коллекции (index)");

        System.out.println("sort :  сортирует коллекцию");
        System.out.println("reorder : реоргангизовать коллекцию");
        System.out.println("clear : очистить коллекцию");

        System.out.println("filter_by_price {price} : вывести элементы, значение поля price которых равно заданному");
        System.out.println("print_ascending : вывести элементы коллекции в порядке возрастания");
        System.out.println("print_field_descending_unit_of_measure : вывести значения поля unitOfMeasure всех элементов в порядке убывания");
        System.out.println("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");

        System.out.println("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        System.out.println("save : сохранить коллекцию в файл");
        System.out.println("exit : выйти из менеджера коллекции");
    }

    /**
     *  Удалить элемент из коллекции по его id
     *  @param commandParts части команд (команда, id продукта)
     */
    private void removeProductById(String[] commandParts) {
        int productId = Integer.parseInt(commandParts[1]);
        productCollection.removeProductById(productId);
    }
    /**
     *  Удалить элемент, находящийся в заданной позиции коллекции (index)
     *  @param commandParts части команд (команда, index продукта)
     */
    private void removeProductAt(String[] commandParts) {
        if (commandParts.length < 2) {
            System.out.println("Ошибка: не указан индекс элемента для удаления.");
            return;
        }

        try {
            int index = Integer.parseInt(commandParts[1]);
            productCollection.removeProductAt(index);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: некорректный индекс. Индекс должен быть целым числом.");
        }
    }
    /**
     * Вывести элементы коллекции в порядке возрастания
     */
    private void printAscending() {
        productCollection.printAscending();
    }
    /**
     * Вывести значения поля unitOfMeasure всех элементов в порядке убывания
     */
    private void printFieldDescendingUnitOfMeasure() {
        productCollection.printFieldDescendingUnitOfMeasure();
    }
    /**
     * Вывести элементы, значение поля price которых равно заданному
     *  @param commandParts части команд (команда, цена продукта)
     */
    private void filterByPrice(String[] commandParts) {
        if (commandParts.length < 2) {
            System.out.println("Ошибка: не указана цена для фильтрации.");
            return;
        }

        try {
            long price = Long.parseLong(commandParts[1]);
            productCollection.filterByPrice(price);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: некорректная цена. Цена должна быть целым числом.");
        }
    }
    /**
     * Отсортировать коллекцию в естественном порядке
     */
    private void sortCollection() {
        productCollection.sortCollection();
    }
    /**
     * Отсортировать коллекцию в порядке, обратном нынешнему
     */
    private void reorderCollection() {
        productCollection.reorderCollection();
    }
    /**
     * Удаляет все элементы коллекции
     */
    private void clearCollection() {
        productCollection.clearCollection();
    }

    /**
     * Сохранение коллекции в файл
     */
    private void saveCollectionToFile() {
        try {
            productCollection.saveToFile(fileName);
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении коллекции: " + e.getMessage());
        }
    }

    /**
     * Выводит всю коллекцию
     */
    private void showCollection() {
        for (Product product : productCollection.getProducts()) {
            System.out.println(product);
        }
    }
    /**
     * Вспомогательный метод для чтения данных об единице измерения продукта
     */
    private UnitOfMeasure promptUnitOfMeasure() {
        System.out.println("Выберите единицу измерения (KILOGRAMS, GRAMS, MILLIGRAMS):");
        while (true) {
            try {
                String input = scanner.nextLine().toUpperCase();
                return UnitOfMeasure.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректное значение. Пожалуйста, выберите из предложенных вариантов.");
            }
        }
    }
    /**
     * Вспомогательный метод для чтения данных о цвете
     */
    private Color promptColor(String promptMessage) {
        System.out.println(promptMessage);
        while (true) {
            try {
                String input = scanner.nextLine().toUpperCase();
                return Color.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректное значение. Пожалуйста, выберите из предложенных вариантов.");
            }
        }
    }

    /**
     * Вспомогательный метод для чтения данных о владельце продукта
     */
    private Person promptPerson() {
        System.out.println("Введите данные о владельце товара:");

        System.out.print("Имя: ");
        String name = scanner.nextLine();

        System.out.print("Паспорт ID: ");
        String passportID = scanner.nextLine();

        Color eyeColor = promptColor("Выберите цвет глаз (GREEN, BLUE, YELLOW, WHITE, BROWN):");

        Color hairColor = promptColor("Выберите цвет волос (GREEN, BLUE, ORANGE):");

        Country nationality = promptCountry();

        Location location = promptLocation();

        return new Person(name, passportID, eyeColor, hairColor, nationality, location);
    }
    /**
     * Вспомогательный метод для чтения данных о стране владельца
     */
    private Country promptCountry() {
        System.out.println("Выберите страну (GERMANY, CHINA, NORTH_KOREA):");
        while (true) {
            try {
                String input = scanner.nextLine().toUpperCase();
                return Country.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректное значение. Пожалуйста, выберите из предложенных вариантов.");
            }
        }
    }

    /**
     * Вспомогательный метод для чтения данных о локации владельца
     */
    private Location promptLocation() {
        System.out.println("Введите данные о местоположении:");

        System.out.print("Координата x: ");
        Long x = Long.parseLong(scanner.nextLine());

        System.out.print("Координата y: ");
        Integer y = Integer.parseInt(scanner.nextLine());

        System.out.print("Название места: ");
        String name = scanner.nextLine();

        return new Location(x, y, name);
    }
    /**
     * Добавляет новый продукт в коллекцию
     */
    private void addProduct() {
        boolean validInput = false;
        System.out.print("Введите название товара: ");
        String name = scanner.nextLine();

        Integer x=0;
        do {
            try {
                System.out.print("Введите координату x: ");
                x = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Некорректный формат числа. Пожалуйста, введите число заново.");
            }
        } while (!validInput);

        validInput = false;
        int y=0;
        do {
            try {
                System.out.print("Введите координату y: ");
                y = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Некорректный формат числа. Пожалуйста, введите число заново.");
            }
        } while (!validInput);

        validInput = false;
        Long price=null;
        do {
            try {
                System.out.print("Введите цену товара: ");
                price = Long.parseLong(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Некорректный формат числа. Пожалуйста, введите число заново.");
            }
        } while (!validInput);


        validInput = false;
        String partNumber=null;
        do {
            try {
                System.out.print("Введите номер партии: ");
                partNumber = scanner.nextLine();
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!validInput);

        validInput = false;
        int manufacturePrice=0;
        do {
            try {
                System.out.print("Введите стоимость производства: ");
                manufacturePrice = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Некорректный формат числа. Пожалуйста, введите число заново.");
            }
        } while (!validInput);

        UnitOfMeasure unitOfMeasure = promptUnitOfMeasure();
        Person owner = promptPerson();

        Product newProduct = new Product(name, new Coordinates(x, y), price, partNumber, manufacturePrice, unitOfMeasure, owner);

        // Добавляем продукт в коллекцию
        productCollection.addProduct(newProduct);
        System.out.println("Новый продукт успешно добавлен в коллекцию.");

    }
    /**
     * Обновляет продукт по его id
     * @param commandParts части команд (команда, id продукта)
     */
    private void updateProduct(String[] commandParts) {
        int productId;
        try {
            productId = Integer.parseInt(commandParts[1]);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат id. Пожалуйста, введите целое число.");
            return;
        }

        Product existingProduct = findProductById(productId);
        if (existingProduct == null) {
            System.out.println("Продукт с id " + productId + " не найден.");
            return;
        }

        System.out.println("Введите данные о продукте:");

        System.out.print("Название товара: ");
        String name = scanner.nextLine();

        System.out.print("Координата x: ");
        Integer x = Integer.parseInt(scanner.nextLine());

        System.out.print("Координата y: ");
        int y = Integer.parseInt(scanner.nextLine());

        Long price;
        while (true) {
            try {
                System.out.print("Цена: ");
                price = Long.parseLong(scanner.nextLine());
                if (price <= 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Некорректное значение цены. Пожалуйста, введите положительное число.");
            }
        }

        String partNumber=null;
        do {
            try {
                System.out.print("Введите номер партии: ");
                partNumber = scanner.nextLine();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        Integer manufactureCost;
        while (true) {
            try {
                System.out.print("Стоимость производства (или Enter для пропуска): ");
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    manufactureCost = null;
                    break;
                }
                manufactureCost = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Некорректное значение стоимости производства. Пожалуйста, введите целое число или нажмите Enter.");
            }
        }
        UnitOfMeasure unitOfMeasure = promptUnitOfMeasure();

        Person owner = promptPerson();

        Product updatedProduct = new Product(name, new Coordinates(x, y), price, partNumber, manufactureCost, unitOfMeasure, owner);

        productCollection.updateProduct(productId, updatedProduct);
        System.out.println("Продукт с id " + productId + " успешно обновлен.");
    }
    /**
     * Находит продукт по его id
     * @param productId id продукта
     */
    private Product findProductById(int productId) {
        for (Product product : productCollection.getProducts()) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Выводит информацию о коллекции (тип, дата инициализации, количество элементов и т.д.).
     */
    private void info() {
        productCollection.info();
    }

    /**
     * Останавливает программу
     */
    private void exit() {
        System.out.println("Завершение работы программы. Коллекция не сохранена.");
        System.exit(0);
    }
}
