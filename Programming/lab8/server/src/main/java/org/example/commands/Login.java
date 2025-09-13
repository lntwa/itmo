package org.example.commands;

import org.example.exceptions.CommandArgumentException;
import org.example.exceptions.InvalidRequestException;
import org.example.network.*;
import org.example.utilities.*;

public class Login extends Command {
    /**
     * Менеджер пользователей
     */
    private final DBmanager users;

    /**
     * Конструктор класса.
     *
     * @param users менеджер пользователей
     */
    public Login(DBmanager users) {
        super("login", false);
        this.users = users;
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
        if (args.length > 0) {
            throw new CommandArgumentException(this.getName(), args.length);
        }

        String login = io.read("Логин: ");
        io.write("Пароль: ");
        String password = String.valueOf(System.console().readPassword());

        return new RequestBody(new String[] {login, password});
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
        if (request.getBody().getArgsLength() != 2) {
            throw new InvalidRequestException("Для этой операции требуется два аргумента: логин и пароль.", "invalidValue");
        }

        AuthCredentials newCredentials = new AuthCredentials(
                request.getBody().getArg(0),
                request.getBody().getArg(1)
        );

        Long currentUserId = users.authenticate(newCredentials);

        if (currentUserId == null) {
            return new Response("Неверный логин или пароль.", "incorrectCredentials", new Object[] {});
        }

        return new ResponseWithAuthCredentials(newCredentials, "Успешный вход в систему.");
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    @Override
    public String getUsage() {
        return TerminalColors.colorString("login", TerminalColors.GREEN)
                + " - запуск входа в систему";
    }
}
