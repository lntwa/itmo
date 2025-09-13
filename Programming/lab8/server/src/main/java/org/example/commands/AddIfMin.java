package org.example.commands;

import org.example.network.Request;
import org.example.network.Response;
import org.example.utilities.*;

/**
 * Класс команды консоли, добавлющей класс в коллекцию, если значение населения
 * меньше значения населения минимального элемента этой коллекции
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class AddIfMin extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public AddIfMin(CollectionManager col) {
        super("add_if_min");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("add_if_min", TerminalColors.GREEN)
                + " - добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    /**
     * Исполнение команды
     */
    @Override
    public Response execute(Request request) {
        return new Response();
    }
}