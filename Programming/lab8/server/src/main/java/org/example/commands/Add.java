package org.example.commands;


import org.example.exceptions.CommandArgumentException;
import org.example.exceptions.InvalidFieldException;
import org.example.exceptions.InvalidRequestException;
import org.example.managers.AskManager;
import org.example.network.*;
import org.example.utilities.*;

/**
 * Класс команды консоли, добавлющей класс в коллекцию
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Add extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public Add(CollectionManager col) {
        super("add");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("add", TerminalColors.GREEN)
                + " - добавить новый элемент в коллекцию";
    }

    /**
     * Упаковывает аргументы команды и данные организации в объект RequestBody.
     *
     * @param args массив аргументов команды
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return объект RequestBody с упакованными данными
     * @throws CommandArgumentException если аргументы команды некорректны
     */
    @Override
    public RequestBody packageBody(String[] args, BasicUserIO io) throws CommandArgumentException {
        if (args.length != 0) {
            throw new CommandArgumentException(this.getName(), args.length);
        }

        try {
            return new RequestBodyWithCity(args, CityMaker.parseCity(io, 1));
        } catch (InvalidFieldException e) {
            io.writeln(TerminalColors.colorString("Ошибка при создании новой коллекции.", TerminalColors.RED));
            return null;
        }
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
        if (request.getBody() == null || !(request.getBody() instanceof RequestBodyWithCity)) {
            throw new InvalidRequestException("К запросу должна быть прикреплен город.", "invalidValue");
        }
        RequestBodyWithCity body = (RequestBodyWithCity) request.getBody();
        body.getCity().setOwner(request.getAuth().getLogin());
        long newId = col.add(body.getCity());
        if (newId == 0) {
            return new Response("Не удалось добавить город.");
        } else {
            return new Response("Добавлен город, присвоен идентификатор " + newId);
        }
    }
}
