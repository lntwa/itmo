package org.example;

import org.example.commands.*;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.managers.DumpManager;
import org.example.utilities.Console;
import org.example.utilities.Runner;

/**
 * Главный класс приложения
 */
public class Main {
    /**
     * Точка входа в приложение
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {

        Console console = new Console();

        if (args.length == 0) {
            console.println("Введите имя загружаемого файла, как аргумент командной строки: ");
            System.exit(1);
        }

        DumpManager dumpManager = new DumpManager(args[0], console);
        CollectionManager collectionManager = new CollectionManager(dumpManager);

        if (!collectionManager.loadCollection()) {
            console.printError("Ошибка при загрузке коллекции. Проверьте файл и попробуйте снова!");
            System.exit(1);
        }

        collectionManager.sort();

        CommandManager commandManager = new CommandManager();
        Runner runner = new Runner(commandManager, console);

        commandManager.register("help", new Help(console, commandManager));
        commandManager.register("info", new Info(collectionManager));
        commandManager.register("show", new Show(console, collectionManager));
        commandManager.register("add", new Add(console, collectionManager));
        commandManager.register("update", new Update(console, collectionManager));
        commandManager.register("remove", new Remove(console, collectionManager));
        commandManager.register("clear", new Clear(console, collectionManager));
        commandManager.register("save", new Save(console, collectionManager));
        commandManager.register("execute_script", new ExecuteScript(console));
        commandManager.register("exit", new Exit(console));
        commandManager.register("remove_first", new RemoveFirst(console, collectionManager));
        commandManager.register("add_if_max", new AddIfMax(console, collectionManager));
        commandManager.register("add_if_min", new AddIfMin(console, collectionManager));
        commandManager.register("filter_starts_with_name", new FilterStartsWithName(console, collectionManager));
        commandManager.register("filter_less_than_timezone", new FilterLessThanTimezone(console, collectionManager));
        commandManager.register("print_field_ascending", new PrintFieldAscending(console, collectionManager));

        runner.interactiveMode();
    }
}