package org.example.managers;

import org.example.commands.Command;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

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
    private final ConcurrentSkipListMap<String, Command> commands = new ConcurrentSkipListMap<>();

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
    public ConcurrentSkipListMap<String, Command> getCommands() {
        return commands;
    }
}