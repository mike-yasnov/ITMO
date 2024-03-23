import processors.CommandProcessor;
import processors.ProductCollection;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String fileName = System.getenv("XML_FILE_PATH");
        if (fileName != null) {
            System.out.println("Путь к файлу XML: " + fileName);
        } else {
            System.err.println("Переменная окружения XML_FILE_PATH не установлена");
            System.exit(0);
        }

        ProductCollection productCollection = new ProductCollection();
        productCollection.loadFromFile(fileName);

        Scanner scanner = new Scanner(System.in);
        CommandProcessor commandProcessor = new CommandProcessor(productCollection, scanner, fileName);
        commandProcessor.run();
    }
}
