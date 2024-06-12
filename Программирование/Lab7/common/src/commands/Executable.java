package commands;
import auxiliary.ExecutionResponse;

/**
 * Интерфейс для всех выполняемых команд.
 */
public interface Executable {
    /**
     * Выполнить что-либо.
     *
     * @param arguments Аргумент для выполнения
     * @return результат выполнения
     */
    ExecutionResponse apply(String[] arguments, String login);
}

