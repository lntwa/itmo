package org.example.commands;


import org.example.managers.CollectionManager;
import org.example.managers.DBmanager;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

/**
 * Класс команды, которая удаляет первый элемент из коллекции
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class RemoveFirst extends Command {
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
    public RemoveFirst(Console console, CollectionManager collectionManager, DBmanager dbmanager) {
        super("remove_first", "удалить первый элемент из коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.dbmanager = dbmanager;
        commandType = CommandTypes.RemoveFirst;
    }

    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами команды
     * @return возвращает информацию о выполнении команды
     */
    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        if (collectionManager.getCollection().isEmpty()) {
            return new ExecutionResponse(false, "Коллекция пуста, удаление невозможно.");
        }
        collectionManager.removeFirst();
        collectionManager.sort();
        return new ExecutionResponse("Первый элемент успешно удален!");
    }
}