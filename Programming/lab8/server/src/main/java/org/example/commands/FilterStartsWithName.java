package org.example.commands;

import org.example.network.Request;
import org.example.network.Response;
import org.example.utilities.CollectionManager;
import org.example.models.City;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;
import org.example.utilities.TerminalColors;

import java.util.List;

/**
 * Класс команды, которая выводит список элементов, поля name которых начинается с заданной подстроки
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class FilterStartsWithName extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public FilterStartsWithName(CollectionManager col) {
        super("filter_starts_with_name");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("filter_starts_with_name", TerminalColors.GREEN)
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