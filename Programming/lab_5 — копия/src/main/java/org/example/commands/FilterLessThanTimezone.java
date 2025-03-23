package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.model.City;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс команды, которая выводит список элементов, поля timezone которых меньше заданного
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class FilterLessThanTimezone extends Command {
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
    public FilterLessThanTimezone(Console console, CollectionManager collectionManager) {
        super("filter_less_than_timezone", "вывести элементы, значение поля timezone которых меньше заданного");
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
        if (arguments.length < 2 || arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + " timezone'");
        }

        double timezone;
        try {
            timezone = Double.parseDouble(arguments[1].trim());
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Неверный формат timezone. Ожидается число!");
        }

        List<City> filteredCities = new ArrayList<>();
        for (City city : collectionManager.getCollection()) {
            if (city.getTimezone() < timezone) {
                filteredCities.add(city);
            }
        }

        if (filteredCities.isEmpty()) {
            return new ExecutionResponse("Нет городов, у которых timezone меньше " + timezone + ".");
        } else {
            StringBuilder result = new StringBuilder("Города, у которых timezone меньше " + timezone + ":\n");
            for (City city : filteredCities) {
                result.append(city).append("\n");
            }
            return new ExecutionResponse(result.toString());
        }
    }
}