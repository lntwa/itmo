package org.example.commands;


import org.example.managers.CollectionManager;
import org.example.managers.DBmanager;
import org.example.models.City;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

/**
 * Класс команды, которая выводит все элементы коллекции
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Show extends Command {
    /**
     * Консоль
     */
    private Console console;
    /**
     * Менеджер коллекции
     */
    private CollectionManager collectionManager;

    private DBmanager dbmanager;

    /**
     * Конструктор
     *
     * @param console консоль
     * @param collectionManager менеджер коллекции
     */
    public Show(Console console, CollectionManager collectionManager, DBmanager dbmanager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
        this.dbmanager = dbmanager;
        commandType = CommandTypes.Show;
    }

    @Override
    public ExecutionResponse apply(String[] arguments,String login) {
        if (!arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new ExecutionResponse(collectionManager.toString());
    }
}
