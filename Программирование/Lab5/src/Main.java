import processors.CommandProcessor;
import processors.ProductCollection;
import utils.FilePathReader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = FilePathReader.getXmlFilePath();

        ProductCollection productCollection = new ProductCollection();
        if (fileName != null) {
            productCollection.loadFromFile(fileName);
        }

        Scanner scanner = new Scanner(System.in);
        CommandProcessor commandProcessor = new CommandProcessor(productCollection, scanner, fileName);
        commandProcessor.run();
    }
}
