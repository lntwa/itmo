package org.example.managers;

import org.example.commands.Command;

import java.util.*;

/**
 * Класс менеджера команд
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class CommandManager {
    /**
     * Коллекция для хранения команд
     */
    private final Map<String, Command> commands = new LinkedHashMap<>();

    /**
     * Регистрирует команду в менеджере
     * @param commandName имя команды
     * @param command объект команды
     */
    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * Возвращает коллекцию зарегистрированных команд
     * @return коллекцию команд
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
}