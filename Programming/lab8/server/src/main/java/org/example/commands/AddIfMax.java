package org.example.commands;


import org.example.network.Request;
import org.example.network.Response;
import org.example.utilities.*;

/**
 * Класс команды консоли, добавлющей класс в коллекцию, если значение населения
 * превышает значение населения наибольшего элемента этой коллекции
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class AddIfMax extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public AddIfMax(CollectionManager col) {
        super("add_if_max");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("add_if_max", TerminalColors.GREEN)
                + " - добавить новый элемент в коллекцию, если его значение больше, чем у наибольшего элемента этой коллекции";
    }

    /**
     * Исполнение команды
     */
    @Override
    public Response execute(Request request) {
        return new Response();
    }
}
