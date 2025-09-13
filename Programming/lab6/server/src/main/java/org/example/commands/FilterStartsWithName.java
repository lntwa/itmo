package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.models.City;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

import java.util.List;

/**
 * Класс команды, которая выводит список элементов, поля name которых начинается с заданной подстроки
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class FilterStartsWithName extends Command {
    /**
     * Консоль
     */
    private final Console console;
    /**
     * Менеджер коллекции
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param console консоль
     * @param collectionManager менеджер коллекции
     */
    public FilterStartsWithName(Console console, CollectionManager collectionManager) {
        super("filter_starts_with_name", "вывести элементы, значение поля name которых начинается с заданной подстроки");
        this.console = console;
        this.collectionManager = collectionManager;
        commandType = CommandTypes.FilterStartsWithName;
    }

    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами команды
     * @return возвращает информацию о выполнении команды
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments.length < 2 || arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + " name'");
        }
        String substring = arguments[1].trim();
        if (substring.isEmpty()) {
            return new ExecutionResponse(false, "Подстрока не может быть пустой!");
        }
        List<City> filteredCities = collectionManager.getCollection().stream()
                .filter(city -> city.getName().toLowerCase().startsWith(substring.toLowerCase()))
                .toList();
        if (filteredCities.isEmpty()) {
            return new ExecutionResponse("Нет городов, название которых начинается с '" + substring + "'.");
        } else {
            StringBuilder result = new StringBuilder("Города, название которых начинается с '" + substring + "':\n");
            for (City city : filteredCities) {
                result.append(city).append("\n");
            }
            return new ExecutionResponse(result.toString());
        }
    }
}