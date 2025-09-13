package org.example.commands;

import org.example.network.Request;
import org.example.network.Response;
import org.example.utilities.*;
import org.example.models.City;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс команды консоли, очищающей коллекцию
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Clear extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public Clear(CollectionManager col) {
        super("clear");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    @Override
    public String getUsage() {
        return TerminalColors.colorString("clear", TerminalColors.GREEN) + " - очистить коллекцию";
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     */
    @Override
    public Response execute(Request request) {
        col.removeIf(x -> x.getOwner().equals(request.getAuth().getLogin()));
        return new Response("Коллекция очищена.");
    }
}
