package models;

public enum Country {
    GERMANY,
    USA,
    CHINA,
    NORTH_KOREA;
    /**
     * @return Строка со всеми элементами enum'а через запятую.
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var countryType : values()) {
            nameList.append(countryType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
