package org.example.commands;

import org.example.managers.AskManager;
import org.example.managers.CollectionManager;
import org.example.model.City;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

/**
 * Класс команды консоли, добавлющей класс в коллекцию
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Add extends Command {
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
    public Add (Console console, CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
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
            if (!arguments[1].isEmpty())
                return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            console.println("Создание нового города: ");
            City c = AskManager.askCity(console, collectionManager.generateNewId());

            if (c != null && c.validate()) {
                collectionManager.add(c);
                return new ExecutionResponse("Город добавлен в коллекцию! ");
            } else return new ExecutionResponse(false, "Поля города не валидны! Город не создан!");
        } catch (AskManager.AskBreak e) {
            return new ExecutionResponse(false, "Отмена ... ");
        }
    }
}
