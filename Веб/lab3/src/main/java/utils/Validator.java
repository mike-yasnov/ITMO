package utils;

public class Validator {
    public static boolean validateX(double x) {
        return x >= -4 && x <= 4;
    }

    public static boolean validateY(double y) {
        return y >= -5 && y <= 5;
    }

    public static boolean validateR(double r) {
        return r >= 1 && r <= 3;
    }
}
