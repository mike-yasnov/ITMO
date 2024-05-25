package commands;

import java.io.Serializable;

public enum CommandTypes implements Serializable {

    ADD("add"),
    CLEAR("clear"),
    FILTER_BY_PRICE("filter_by_price"),
    HISTORY("history"),
    INFO("info"),
    PRINT_FIELD_DESCENDING_UNIT_OF_MEASURE("print_field_descending_unit_of_measure"),
    REMOVE_BY_ID("remove_by_id"),
    SHOW("show"),
    UPDATE("update"),
    EXIT("exit"),
    HELP("help"),
    SAVE("save"),
    REORDER("reorder"),
    SORT("sort"),
    REMOVE_AT("remove_at"),
    EXECUTE_SCRIPT("execute_script"),
    PRINT_ASCENDING("print_ascending");

    private String type;

    private CommandTypes(String type) {
        this.type = type;
    }

    public String Type() {
        return type;
    }

    private static final long serialVersionUID = 14L;

    public static CommandTypes getByString(String string) {
        try {
            return CommandTypes.valueOf(string.toUpperCase());
        } catch (NullPointerException | IllegalArgumentException e) {
            return null;
        }
    }

}
