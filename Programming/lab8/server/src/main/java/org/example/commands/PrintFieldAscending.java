package org.example.commands;

import org.example.network.Request;
import org.example.network.Response;
import org.example.utilities.CollectionManager;
import org.example.models.City;
import org.example.models.StandardOfLiving;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;
import org.example.utilities.TerminalColors;

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
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public PrintFieldAscending(CollectionManager col) {
        super("print_field_ascending");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("print_field_ascending", TerminalColors.GREEN)
                + " - вывести значения поля standardOfLiving всех элементов в порядке возрастания";
    }

    /**
     * Исполнение команды
     */
    @Override
    public Response execute(Request request) {
        return new Response();
    }
}