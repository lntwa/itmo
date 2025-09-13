package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.managers.DBmanager;
import org.example.models.City;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс команды консоли, очищающей коллекцию
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Clear extends Command {
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
    public Clear(Console console, CollectionManager collectionManager, DBmanager dbmanager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
        this.dbmanager = dbmanager;
        commandType = CommandTypes.Clear;
    }
    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами команды
     * @return возвращает информацию о выполнении команды
     */
    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        if (!arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        Iterator<City> iterator = collectionManager.getCollection().iterator();
        CopyOnWriteArrayList<Integer> ids = new CopyOnWriteArrayList<>();
        while (iterator.hasNext()) {
            City city = iterator.next();
            ids.add(city.getId());

        }
        for(var e: ids){
            collectionManager.remove(e);
        }
        collectionManager.sort();
        dbmanager.clear(login);
        return new ExecutionResponse("Коллекция очищена!");
    }
}
