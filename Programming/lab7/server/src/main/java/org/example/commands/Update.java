package org.example.commands;


import org.example.managers.CollectionManager;
import org.example.managers.DBmanager;
import org.example.models.City;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

/**
 * Класс команды, которая обновляет значение элемента коллекции по заданному id
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Update extends Command {
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
    public Update(Console console, CollectionManager collectionManager, DBmanager dbmanager) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
        this.console = console;
        this.collectionManager = collectionManager;
        this.dbmanager = dbmanager;
        commandType = CommandTypes.Update;
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
            if (arguments.length < 2 || arguments[1].isEmpty()) {
                return new ExecutionResponse(false, "Не получены данные города!");
            }

            City newCity = City.fromArray(arguments[1].split(","), login);
            if (newCity == null) {
                return new ExecutionResponse(false, "Неверный формат данных города!");
            }

            City oldCity = collectionManager.getById(newCity.getId());
            if (oldCity == null) {
                return new ExecutionResponse(false, "Города с ID " + newCity.getId() + " не существует!");
            }

            if (!newCity.validate()) {
                return new ExecutionResponse(false, "Невалидные данные города!");
            }

            if(!oldCity.getOwner().equals(login)) {return new ExecutionResponse(false,"У вас нет прав для обновления этого элемента");}
            if (newCity != null && newCity.validate()) {
                collectionManager.remove(oldCity.getId());
                collectionManager.add(newCity);
                collectionManager.sort();
                if(dbmanager.update(oldCity.getId(),newCity)) return new ExecutionResponse("Обновлено!");
                else return new ExecutionResponse(false,"Не удалось записать в базу данных!");
            }
            return new ExecutionResponse(true, "Город с ID " + newCity.getId() + " успешно обновлен!");
        } catch (Exception e) {
            return new ExecutionResponse(false, "Ошибка при обновлении: " + e.getMessage());
        }
    }
}
