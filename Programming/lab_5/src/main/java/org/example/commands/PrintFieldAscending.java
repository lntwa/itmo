package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.model.City;
import org.example.model.StandardOfLiving;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Класс команды, которая выводит значения поля standardOfLiving всех элементов в порядке возрастания
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class PrintFieldAscending extends Command {
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
    public PrintFieldAscending(Console console, CollectionManager collectionManager) {
        super("print_field_ascending", "вывести значения поля standardOfLiving всех элементов в порядке возрастания");
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
        if (arguments.length > 1 && !arguments[1].isEmpty()) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        List<StandardOfLiving> standardOfLivingValues = new ArrayList<>();
        for (City city : collectionManager.getCollection()) {
            standardOfLivingValues.add(city.getStandardOfLiving());
        }

        // задаем четкий порядок сортировки в порядке возрастания
        List<StandardOfLiving> orderedValues = List.of(
                StandardOfLiving.ULTRA_LOW,
                StandardOfLiving.LOW,
                StandardOfLiving.HIGH,
                StandardOfLiving.VERY_HIGH
        );

        standardOfLivingValues.sort(Comparator.comparingInt(orderedValues::indexOf));  // сортировка по числовому индексу

        if (standardOfLivingValues.isEmpty()) {
            return new ExecutionResponse("Коллекция пуста...");
        } else {
            StringBuilder result = new StringBuilder("Значения standardOfLiving в порядке возрастания:\n");
            for (StandardOfLiving value : standardOfLivingValues) {
                result.append(value).append("\n");
            }
            return new ExecutionResponse(result.toString());
        }
    }

}