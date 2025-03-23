package org.example.managers;

import org.example.exceptions.NoSuchElementException;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;
import org.example.utilities.Runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * Класс, который управляет скриптом
 */
public class ScriptManager {
    /**
     * Консоль
     */
    private final Console console;
    /**
     * Стек выполняемых скриптов
     */
    private final Deque<String> scriptStack = new ArrayDeque<>();

    /**
     * Конструктор класса ScriptManager.
     *
     * @param console консоль для вывода сообщений
     */
    public ScriptManager(Console console) {
        this.console = console;
    }

    /**
     * Проверяет, есть ли рекурсия в стеке вызовов скриптов
     * Если аргумент содержится в стеке, то рекурсия запрещается
     * @param argument название скрипта, который запускается
     * @param scriptScanner сканер для чтения из скрипта
     * @return true, если может быть рекурсия, иначе false
     */
    private boolean checkRecursion(String argument, Scanner scriptScanner) {
        // если аргумент уже содержится в стеке скриптов, это рекурсия
        if (scriptStack.contains(argument)) {
            console.selectConsoleScanner(); // переключаемся на консольный ввод
            console.println("Обнаружена рекурсия! Скрипт '" + argument + "' уже выполняется.");
            console.selectFileScanner(scriptScanner); // возвращаемся к сканеру файла
            return true; // рекурсия обнаружена
        }
        return false; // рекурсии нет
    }

    /** Метод для выполнения скрипта
     * @param fileName название файла со скриптом
     * @param runner объект класса Runner
     * @return результат выполнения скрипта
     */
    public ExecutionResponse executeScript(String fileName, Runner runner) {
        console.println("Проверяем путь: " + fileName);
        Path path = Paths.get(fileName);
        if (!path.isAbsolute()) {
            path = Paths.get(System.getProperty("user.dir"), fileName);
        }
        console.println("Абсолютный путь файла со скриптом: " + path.toAbsolutePath());
        File scriptFile = path.toFile();
        if (!scriptFile.exists()) {
            return new ExecutionResponse(false,"Файла нет: " + path.toAbsolutePath());
        }
        if (!scriptFile.canRead()) {
            return new ExecutionResponse(false, "Нет прав на чтение файла: " + path.toAbsolutePath());
        }
        scriptStack.addLast(fileName);
        console.println("Файл найден, начинается выполнение скрипта ...");

        try (Scanner scannerForScript = new Scanner(scriptFile)) {
            ExecutionResponse statusOfCommand;
            if (!scannerForScript.hasNext()) throw new NoSuchElementException();
            //переключаем чтение из консоли на чтение из файла
            console.selectFileScanner(scannerForScript);
            String[] userCommand;

            do {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                //выводим команду с приглашением
                console.println(console.getPrompt() + String.join(" ", userCommand));

                boolean isLaunchNeeded = true;
                if (userCommand[0].equals("execute_script")) {
                    isLaunchNeeded = checkRecursion(userCommand[1], scannerForScript);
                }
                //запускается команда
                statusOfCommand = isLaunchNeeded ? runner.launchCommand(userCommand) : new ExecutionResponse(false, "");
                if (userCommand[0].equals("execute_script")) console.selectFileScanner(scannerForScript);
                console.println(statusOfCommand.getMessage());

                if (!statusOfCommand.getExitCode()) {
                    console.printError("Проверьте скрипт на корректность введенных данных и на рекурсию!");
                }
            } while (console.isCanReadln() && statusOfCommand.getExitCode() && !userCommand[0].equals("exit"));

            //снова чтение из консоли
            console.selectConsoleScanner();
            return new ExecutionResponse("");

        } catch (FileNotFoundException e) {
            return new ExecutionResponse(false, "Файл не найден: " + path.toAbsolutePath());
        } catch (NoSuchElementException e) {
            return new ExecutionResponse(false, "Файл пустой: " + path.toAbsolutePath());
        } catch (Exception e) {
            return new ExecutionResponse(false, "Ошибка выполнения скрипта " + e.getMessage());
        } finally {
            scriptStack.pollLast();
        }
    }
}