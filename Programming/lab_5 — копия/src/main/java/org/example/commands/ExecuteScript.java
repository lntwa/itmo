package org.example.commands;

import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;
import org.example.utilities.Runner;

/**
 * Класс команды выполнения скрипта из файла
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class ExecuteScript extends Command {
    /**
     * Консоль
     */
    private final Console console;

    /**
     * Конструктор
     *
     * @param console консоль
     */
    public ExecuteScript(Console console) {
        super("execute_script", "исполнить скрипт из указанного файла");
        this.console = console;
    }

    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами команды
     * @return возвращает информацию о выполнении команды
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments.length < 2 || arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + " <file_path>'");
        }
        return new ExecutionResponse("");
    }
}