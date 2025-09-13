package org.example.commands;


import org.example.exceptions.CommandArgumentException;
import org.example.exceptions.InvalidRequestException;
import org.example.exceptions.UnauthorizedException;
import org.example.models.City;
import org.example.network.*;
import org.example.utilities.*;

import java.util.NoSuchElementException;

/**
 * Класс команды, которая удаляет первый элемент из коллекции
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class RemoveFirst extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public RemoveFirst(CollectionManager col) {
        super("remove_first");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    @Override
    public String getUsage() {
        return TerminalColors.colorString("remove_first", TerminalColors.GREEN)
                + " - удалить первый элемент из коллекции";
    }

    /**
     * Выполняет команду
     *
     * @param request объект Request с запросом от клиента
     * @return объект Response с результатом выполнения команды
     * @throws InvalidRequestException если запрос некорректен
     */
    @Override
    public Response execute(Request request) throws InvalidRequestException {
        return null;
    }
}