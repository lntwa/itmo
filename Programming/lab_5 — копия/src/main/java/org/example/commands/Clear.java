package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.model.City;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

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

    /**
     * Конструктор
     *
     * @param console консоль
     * @param collectionManager менеджер коллекции
     */
    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами команды
     * @return возвращает информацию о выполнении команды
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        for (City city : collectionManager.getCollection()) {
            collectionManager.remove(city.getId());
        }
        collectionManager.sort();
        return new ExecutionResponse("Коллекция очищена!");
    }
}
