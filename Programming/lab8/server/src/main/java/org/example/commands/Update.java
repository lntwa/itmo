package org.example.commands;


import org.example.exceptions.CommandArgumentException;
import org.example.exceptions.InvalidFieldException;
import org.example.exceptions.InvalidRequestException;
import org.example.network.*;
import org.example.utilities.*;
import org.example.models.City;

/**
 * Класс команды, которая обновляет значение элемента коллекции по заданному id
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Update extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public Update(CollectionManager col) {
        super("update");
        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("update [id]", TerminalColors.GREEN)
                + " - обновить значение элемента коллекции, id которого равен заданному";
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
            throw new CommandArgumentException(getName(), 1, args.length);
        }

        try {
            Integer id = Integer.parseInt(args[0]);
            City city = CityMaker.parseCity(io, id);
            return new RequestBodyWithCity(args, city);
        } catch (NumberFormatException e) {
            throw new CommandArgumentException("Идентификатор не является действительным номером", e);
        } catch (InvalidFieldException e) {
            io.writeln(TerminalColors.colorString("Не удалось обновить организацию.", TerminalColors.RED));
            io.writeln(e);
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
            throw new InvalidRequestException("К запросу должна быть прикреплена организация.", null);
        }

        City city = ((RequestBodyWithCity) request.getBody()).getCity();
        city.setOwner(request.getAuth().getLogin());

        if (!col.update(Integer.valueOf(request.getBody().getArg(0)), city)) {
            throw new InvalidRequestException(new CommandArgumentException("В коллекции не было найдено ни одного элемента с указанным идентификатором."), null);
        }
        return new Response("Updated");
    }
}
