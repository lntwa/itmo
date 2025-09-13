package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.managers.DBmanager;
import org.example.models.City;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

import static org.example.Server.logger;

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

    private DBmanager dbmanager;

    /**
     * Конструктор
     *
     * @param console консоль
     * @param collectionManager менеджер коллекции
     */
    public AddIfMin(Console console, CollectionManager collectionManager, DBmanager dbmanager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.dbmanager = dbmanager;
        commandType = CommandTypes.AddIfMin;
    }
    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами команды
     * @return возвращает информацию о выполнении команды
     */
    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        try {
            if (arguments.length < 2 || arguments[1] == null || arguments[1].trim().isEmpty()) {
                return new ExecutionResponse(false, "Неверные аргументы команды. Использование: 'add' или 'add данные'");
            }

            logger.info("Получены данные для создания города: " + arguments[1]);

            String[] cityData = arguments[1].split(",");
            if (cityData.length < 10) {
                return new ExecutionResponse(false, "Недостаточно данных для создания города. Требуется 10+ параметров");
            }

            City city = City.fromArray(cityData, login);
            if (city == null) {
                return new ExecutionResponse(false, "Ошибка при создании города из переданных данных");
            }

            int newId = dbmanager.insertCity(city); // теперь insertCity возвращает id
            city.setId(newId);
            if (!city.validate()) {
                return new ExecutionResponse(false, "Невалидные данные города");
            }

            if (collectionManager.getFirst().getPopulation() < city.getPopulation()) {
                return new ExecutionResponse(false, "Население введенного города больше чем значение из коллекции с минимальным значением населения! ");
            }

            collectionManager.add(city);
            return new ExecutionResponse(true, "Город успешно добавлен с ID " + city.getId());

        } catch (Exception e) {
            logger.severe("Ошибка при выполнении команды add: " + e.getMessage());
            return new ExecutionResponse(false, "Внутренняя ошибка при добавлении города");
        }
    }
}