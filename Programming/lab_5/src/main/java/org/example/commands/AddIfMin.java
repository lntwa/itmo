package org.example.commands;

import org.example.managers.AskManager;
import org.example.managers.CollectionManager;
import org.example.model.City;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

import java.util.Comparator;

/**
 * Класс команды консоли, добавлющей класс в коллекцию, если значение населения
 * меньше значения населения минимального элемента этой коллекции
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class AddIfMin extends Command {
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
    public AddIfMin(Console console, CollectionManager collectionManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
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
        try {
            if (arguments.length > 1 && !arguments[1].isEmpty()) {
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            }

            console.println("Создание нового города: ");
            City c = AskManager.askCity(console, collectionManager.generateNewId());

            if (c != null && c.validate()) {
                City minCity = collectionManager.getCollection().stream()
                        .min(Comparator.comparingLong(City::getPopulation))
                        .orElse(null);

                if (minCity == null || c.getPopulation() < minCity.getPopulation()) {
                    collectionManager.add(c);
                    return new ExecutionResponse("Город успешно добавлен!");
                } else {
                    return new ExecutionResponse("Население города больше минимального, элемент не добавлен!");
                }
            } else {
                return new ExecutionResponse(false, "Поля города не валидны, город не создан!");
            }
        } catch (AskManager.AskBreak e) {
            return new ExecutionResponse(false, "Отмена ... ");
        }
    }
}