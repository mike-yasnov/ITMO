package subjects.enums;

/**
 * Enum, представляющий единицы измерения.
 */
public enum UnitOfMeasure {
    KILOGRAMS(0),
    GRAMS(1),
    MILLIGRAMS(2);

    private final int value;

    UnitOfMeasure(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

