package org.example.commands;

import org.example.managers.AskManager;
import org.example.managers.CollectionManager;
import org.example.model.City;
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

    /**
     * Конструктор
     *
     * @param console консоль
     * @param collectionManager менеджер коллекции
     */
    public Update(Console console, CollectionManager collectionManager) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
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
            if (arguments[1].isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\n");
            int id;
            try {
                id = Integer.parseInt(arguments[1].trim());
            } catch (NumberFormatException e) {
                return new ExecutionResponse(false, "id  не распознан!");
            }

            var previous_id = collectionManager.getById(id);
            if (!collectionManager.getCollection().contains(previous_id) || previous_id == null) {
                return new ExecutionResponse(false, "Такого id не существует!");
            }

            console.println("Обновление города с id " + id);
            var c = AskManager.askCity(console, previous_id.getId());
            if (c != null && c.validate()) {
                collectionManager.remove(previous_id.getId());
                collectionManager.add(c);
                collectionManager.sort();
                return new ExecutionResponse("Город обновлен!");
            }
        } catch (AskManager.AskBreak e) {
            return new ExecutionResponse(false, "Поля не валидны!");
        }
        return new ExecutionResponse("");
    }
}
