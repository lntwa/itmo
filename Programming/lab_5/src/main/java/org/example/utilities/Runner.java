package org.example.utilities;

import org.example.managers.CommandManager;
import org.example.managers.ScriptManager;
import java.util.NoSuchElementException;

/**
 * Класс исполнения программы
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Runner {
    private final Console console;
    private final CommandManager commandManager;
    private final ScriptManager scriptManager;

    /**
     * Конструктор класса Runner
     * @param commandManager менеджер команд
     * @param console консоль
     */
    public Runner(CommandManager commandManager, Console console) {
        this.console = console;
        this.commandManager = commandManager;
        this.scriptManager = new ScriptManager(console);
    }

    /**
     * Метод, который отвечает за запуск команд
     * @param userCommand массив строк, представляющих из себя команду и её аргументы
     * @return результат выполнения команды
     */
    public ExecutionResponse launchCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return new ExecutionResponse("");
        var command = commandManager.getCommands().get(userCommand[0]);
        if (command == null) {
            return new ExecutionResponse(false, "Команда " + userCommand[0] + " не найдена");
        }
        if (userCommand[0].equals("execute_script")) {
            ExecutionResponse response1 = commandManager.getCommands().get("execute_script").apply(userCommand);
            if (!response1.getExitCode()) return response1;
            ExecutionResponse response2 = scriptManager.executeScript(userCommand[1], this);
            return new ExecutionResponse(response2.getExitCode(), response1.getMessage() + "\n" + response2.getMessage().trim());
        }
        return command.apply(userCommand);
    }

    /**
     * Метод для интерактивного режима работы программы,
     * в котором программа ожидает ввода команд от пользователя и выполняет их
     */
    public void interactiveMode() {
        try {
            ExecutionResponse statusOfCommand;
            var userCommand = new String[]{"", ""};
            while (true) {
                console.prompt();
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                statusOfCommand = launchCommand(userCommand);
                if (userCommand[0].equals("exit"))
                    break;
                console.println(statusOfCommand.getMessage());
            }
        } catch (NoSuchElementException e) {
            console.printError("Пользовательский ввод не найден");
        } catch (IllegalStateException e) {
            console.printError("Непредвиденная ошибка");
        }
    }
}
