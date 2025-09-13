package org.example.commands;

import org.example.exceptions.CommandArgumentException;
import org.example.exceptions.InvalidRequestException;
import org.example.network.BasicUserIO;
import org.example.network.Request;
import org.example.network.RequestBody;
import org.example.network.Response;
import org.example.utilities.ExecutionResponse;

import java.util.Objects;

/**
 * Абстрактный класс команды, представляющий команду с именем и описанием
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public abstract class Command {
    /**
     * Имя команды
     */
    private final String name;

    /**
     * Требование аутентификации
     */
    private final boolean requireAuth;

    /**
     * Конструктор класса.
     *
     * @param name имя команды
     */
    public Command(String name) {
        this.name = name;
        requireAuth = true;
    }

    /**
     * Конструктор класса.
     *
     * @param name имя команды
     * @param requireAuth требуется ли аутентификация для выполнения команды
     */
    public Command(String name, boolean requireAuth) {
        this.name = name;
        this.requireAuth = requireAuth;
    }

    /**
     * Получить имя команды.
     *
     * @return имя команды
     */
    public String getName() {
        return name;
    }

    /**
     * Проверить, требуется ли аутентификация для выполнения команды.
     *
     * @return true, если требуется аутентификация, иначе false
     */
    public boolean requiresAuth() {
        return requireAuth;
    }

    /**
     * Упаковка аргументов команды в тело запроса.
     *
     * @param args аргументы команды
     * @param io объект BasicUserIO для взаимодействия с пользователем
     * @return тело запроса
     * @throws CommandArgumentException при некорректных аргументах команды
     */
    public RequestBody packageBody(String[] args, BasicUserIO io) throws CommandArgumentException {
        if (args.length != 0) {
            throw new CommandArgumentException(this.getName(), args.length);
        }
        return new RequestBody(args);
    }

    /**
     * Выполнение команды на основе полученного запроса.
     *
     * @param request запрос
     * @return ответ на запрос
     * @throws InvalidRequestException при некорректном запросе
     */
    public abstract Response execute(Request request) throws InvalidRequestException;

    /**
     * Получение информации о использовании команды.
     *
     * @return информация о использовании команды
     */
    public abstract String getUsage();
}
