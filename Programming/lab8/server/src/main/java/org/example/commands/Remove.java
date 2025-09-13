package org.example.commands;

import org.example.exceptions.CommandArgumentException;
import org.example.exceptions.InvalidRequestException;
import org.example.exceptions.UnauthorizedException;
import org.example.models.City;
import org.example.network.*;
import org.example.utilities.CollectionManager;
import org.example.utilities.TerminalColors;

import java.util.NoSuchElementException;

/**
 * Команда удаления по id
 */
public class Remove extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public Remove(CollectionManager col) {
        super("remove_by_id");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("remove_by_id [id]", TerminalColors.GREEN)
                + " - удалить элемент из коллекции по его id";
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
        if (args.length != 1) {
            throw new CommandArgumentException(this.getName(), args.length);
        }

        try {
            Long.parseLong(args[0]);
            return new RequestBody(args);
        } catch (NumberFormatException e) {
            throw new CommandArgumentException("Не удалось преобразовать " + args[0] + " в число", e);
        } catch (NoSuchElementException e) {
            throw new CommandArgumentException("Не удается найти элемент с идентификатором " + args[0] + " в коллекции", e);
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
        try {
            City toRemove = col.getItemById(Integer.valueOf(request.getBody().getArg(0)));
            if (toRemove == null) {
                throw new NoSuchElementException();
            }

            if (!toRemove.getOwner().equals(request.getAuth().getLogin())) {
                return new ResponseWithException(new UnauthorizedException());
            }

            col.remove(toRemove.getId());
            return new Response();
        } catch (NumberFormatException e) {
            throw new InvalidRequestException(new CommandArgumentException("Не удалось преобразовать " + request.getBody().getArg(0) + " в число", e), "invalidValue");
        } catch (NoSuchElementException e) {
            throw new InvalidRequestException(new CommandArgumentException("Не удается найти элемент с идентификатором " + request.getBody().getArg(0) + " в коллекции", e), null);
        }
    }
}