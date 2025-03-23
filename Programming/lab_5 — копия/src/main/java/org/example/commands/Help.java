package org.example.commands;

import org.example.managers.CommandManager;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

import java.util.stream.Collectors;

/**
 * Класс команды, которая выводит справку по доступным командам
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Help extends Command {
    /**
     * Консоль
     */
    private final Console console;
    /**
     * Менеджер коллекции
     */
    private final CommandManager commandManager;

    /**
     * Конструктор
     *
     * @param console консоль
     * @param commandManager менеджер команд
     */
    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами команды
     * @return возвращает информацию о выполнении команды
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        return new ExecutionResponse(commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("\n")));
    }
}
