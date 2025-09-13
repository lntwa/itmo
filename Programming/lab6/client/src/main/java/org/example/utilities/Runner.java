package org.example.utilities;

import org.example.commands.*;
import org.example.managers.AskManager;
import org.example.managers.CollectionManager;
import org.example.managers.NetworkManager;
import org.example.models.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Класс исполнения программы
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Runner {
    /**
     * консоль
     */
    private Console console;
    /**
     * менеджер командq
     */
    /**
     * стек скрипта
     */
    private Map<CommandTypes,String[]> commands;
    private final Deque<String> scriptStack = new ArrayDeque<>();
    private NetworkManager networkManager;
    private CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console        консоль
     */
    public Runner(NetworkManager networkManager, Console console, Map<CommandTypes,String[]> commands) {
        this.console = console;
        this.networkManager = networkManager;
        this.commands = commands;
    }

    /**
     * Интерактивный режим
     */
    public void interactiveMode() {
        try {

            ExecutionResponse commandStatus;

            String[] userCommand = {"", ""};

            while (true) {
                console.prompt();
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                console.println(userCommand[0]);
                commandStatus = launchCommand(userCommand);
                if (commandStatus.getMessage() == "exit") break;
                console.println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        }
    }

    /**
     * Проверяет, есть ли рекурсия в стеке вызовов скриптов
     * Если аргумент содержится в стеке, то рекурсия запрещается
     * @param argument название скрипта, который запускается
     * @param scriptScanner сканер для чтения из скрипта
     * @return true, если может быть рекурсия, иначе false
     */
    private boolean checkRecursion(String argument, Scanner scriptScanner) throws Exception {
        // если аргумент уже содержится в стеке скриптов, это рекурсия
        try{
            if (scriptStack.contains(argument)) {
                console.selectConsoleScanner(); // переключаемся на консольный ввод
                console.println("Обнаружена рекурсия! Скрипт '" + argument + "' уже выполняется.");
                throw new Exception("Рекурсия!!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false; // рекурсии нет
    }


    private ExecutionResponse scriptMode(String argument) {
        String[] userCommand = {"", ""};
        StringBuilder executionOutput = new StringBuilder();

        if (!new File(argument).exists()) return new ExecutionResponse(false, "Файл не существет!");
        if (!Files.isReadable(Paths.get(argument))) return new ExecutionResponse(false, "Прав для чтения нет!");

        scriptStack.add(argument);
        try (Scanner scriptScanner = new Scanner(new File(argument))) {

            ExecutionResponse commandStatus;

            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            console.selectFileScanner(scriptScanner);
            do {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (console.isCanReadln() && userCommand[0].isEmpty()) {
                    userCommand = (console.readln().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                executionOutput.append(console.getPrompt() + String.join(" ", userCommand) + "\n");
                var needLaunch = true;
                if (userCommand[0].equals("execute_script")) {
                    needLaunch = checkRecursion(userCommand[1], scriptScanner);
                }
                commandStatus = needLaunch ? launchCommand(userCommand) : new ExecutionResponse("Превышена максимальная глубина рекурсии");
                if (userCommand[0].equals("execute_script")) console.selectFileScanner(scriptScanner);
                executionOutput.append(commandStatus.getMessage() + "\n");
            } while (commandStatus.getExitCode() && !commandStatus.getMessage().equals("exit") && console.isCanReadln());

            console.selectConsoleScanner();
            if (!commandStatus.getExitCode() && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                executionOutput.append("Проверьте скрипт на корректность введенных данных!\n");
            }

            return new ExecutionResponse(commandStatus.getExitCode(), executionOutput.toString());
        } catch (FileNotFoundException exception) {
            return new ExecutionResponse(false, "Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            return new ExecutionResponse(false, "Файл со скриптом пуст!");
        } catch (Exception exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return new ExecutionResponse("");
    }

    /**
     * Функиция загрузки команды
     *
     * @param userCommand загружаемая команда
     * @return возвращает ответ о выполнении программы
     */
    private ExecutionResponse launchCommand(String[] userCommand) {
        ExecutionResponse response;
        if (userCommand[0].equals("")) return new ExecutionResponse("");
        var command = CommandTypes.getByString(userCommand[0]);
        if(!commands.containsKey(command)) {
            command = null;
        }

        if (command == null)
            return new ExecutionResponse(false, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");

        switch (userCommand[0]) {

            case "execute_script" -> {
                ExecutionResponse tmp = new ExecuteScript(console).apply(userCommand);
                if (!tmp.getExitCode()) return tmp;
                ExecutionResponse tmp2 = scriptMode(userCommand[1]);
                return new ExecutionResponse(tmp2.getExitCode(), tmp.getMessage() + "\n" + tmp2.getMessage().trim());
            }
            default -> {
                byte[] bytes = new byte[userCommand.length];
                if (command == CommandTypes.Add || command == CommandTypes.AddIfMax || command == CommandTypes.AddIfMin) {
                    try {
                        bytes = NetworkManager.serializer(new Container(command, AskManager.askCity(console, 0).toStr()));
                    } catch (AskManager.AskBreak e) {
                        console.println("Отмена...");
                    }
                } else if (command == CommandTypes.Update) {
                    try {
                        int id = Integer.parseInt(userCommand[1]);
                        console.println(id);
                        City city = AskManager.askCity(console, id);

                        String cityData = city.toStr();
                        bytes = NetworkManager.serializer(new Container(command, cityData));
                    } catch (NumberFormatException e) {
                        console.println("Ошибка: ID должен быть числом!");
                    } catch (AskManager.AskBreak e) {
                        console.println("Отмена...");
                    }
                } else if (command == CommandTypes.Help) {
                    console.println(new Help(console,commands).apply(userCommand).getMessage());
                } else if (command == CommandTypes.Exit) {
                    bytes = NetworkManager.serializer(new Container(CommandTypes.Save, ""));
                    networkManager.sendData(bytes);
                    return new Exit(console).apply(userCommand);
                } else if (command == CommandTypes.Remove | command == CommandTypes.RemoveFirst) {
                    bytes = NetworkManager.serializer(new Container(command, userCommand[1]));
                } else if (command == CommandTypes.FilterStartsWithName || command == CommandTypes.FilterLessThanTimezone) {
                    try {
                        bytes = NetworkManager.serializer(new Container(command, userCommand[1]));
                    } catch (NumberFormatException e) {
                        console.println("Ошибка! Введите второй аргумент! ");
                    }
                } else {
                    bytes = NetworkManager.serializer(new Container(command, ""));
                }
                if (command != CommandTypes.Help) {
                    networkManager.sendData(bytes);
                    var data = networkManager.receiveData(5069);
                    response = NetworkManager.deserialize(data);
                    return response;
                }
                else return new ExecutionResponse(false,"");
            }
        }
    }
}