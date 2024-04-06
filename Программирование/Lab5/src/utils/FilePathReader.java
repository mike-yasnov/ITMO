package utils;

import java.io.File;

public class FilePathReader {
    public static String getXmlFilePath() {
        String filePath = System.getenv("XML_FILE_PATH");
        if (filePath == null) {
            System.err.println("Переменная окружения XML_FILE_PATH не установлена");
            return null;
        }

        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            System.err.println("Файл с путем " + filePath + " не существует или является директорией");
            return null;
        }

        return filePath;
    }
}

