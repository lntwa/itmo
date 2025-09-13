package org.example.commands;


import org.example.managers.CollectionManager;
import org.example.managers.DBmanager;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

/**
 * Класс команды, которая удаляет элемент коллекции по его id
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Remove extends Command {
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
    public Remove(Console console, CollectionManager collectionManager, DBmanager dbmanager) {
        super("remove", "удалить элемент из коллекции по его id");
        this.console = console;
        this.collectionManager = collectionManager;
        this.dbmanager = dbmanager;
        commandType = CommandTypes.Remove;
    }

    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами команды
     * @return возвращает информацию о выполнении команды
     */
    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        if (arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        int id = -1;
        try {
            id = Integer.parseInt(arguments[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "ID не распознан");
        }


        if (collectionManager.getById(id) == null || !collectionManager.getCollection().contains(collectionManager.getById(id)))
            return new ExecutionResponse(false, "Не существующий ID");
        if(!collectionManager.getById(id).getOwner().equals(login)) return new ExecutionResponse(false,"У вас нет прав для удаления данного элемента");
        if (!dbmanager.deleteById(id))
            return new ExecutionResponse(false, "При удалении города возникла ошибка");
        collectionManager.remove(id);
        collectionManager.sort();

        return new ExecutionResponse("Город успешно удален!");
    }
}
