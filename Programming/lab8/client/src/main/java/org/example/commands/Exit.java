package org.example.commands;

import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

import java.util.logging.Logger;

/**
 * Класс команды завершения программы
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Exit extends Command {
    /**
     * Консоль
     */
    private final Console console;

    public static final Logger logger = Logger.getLogger(Exit.class.getName());

    /**
     * Конструктор
     *
     * @param console консоль
     */
    public Exit(Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
        commandType = CommandTypes.Exit;
    }
    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами команды
     * @return возвращает информацию о выполнении команды
     */
    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        try {
            if (!arguments[1].isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            System.exit(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ExecutionResponse("");
    }
}
