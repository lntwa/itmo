package org.example.commands;


import org.example.models.City;
import org.example.network.Request;
import org.example.network.Response;
import org.example.network.ResponseWithCities;
import org.example.utilities.*;

/**
 * Класс команды, которая выводит все элементы коллекции
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Show extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public Show(CollectionManager col) {
        super("show");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("show", TerminalColors.GREEN)
                + " - вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     */
    @Override
    public Response execute(Request request) {
        City[] a = new City[col.getCollection().size()];
        return new ResponseWithCities(col.getCollection().toArray(a));
    }
}