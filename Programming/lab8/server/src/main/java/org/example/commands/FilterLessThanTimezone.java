package org.example.commands;

import org.example.network.Request;
import org.example.network.Response;
import org.example.utilities.CollectionManager;
import org.example.utilities.TerminalColors;

/**
 * Класс команды, которая выводит список элементов, поля timezone которых меньше заданного
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class FilterLessThanTimezone extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public FilterLessThanTimezone(CollectionManager col) {
        super("filter_if_less_than_timezone");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("filter_if_less_than_timezone", TerminalColors.GREEN)
                + " - вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    /**
     * Исполнение команды
     */
    @Override
    public Response execute(Request request) {
        return new Response();
    }
}