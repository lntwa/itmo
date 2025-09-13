package org.example.managers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.example.commands.*;
import org.example.exceptions.CommandArgumentException;
import org.example.exceptions.CommandNotFoundException;
import org.example.exceptions.InvalidRequestException;
import org.example.exceptions.UnauthenticatedException;
import org.example.network.*;
import org.example.utilities.AuthCredentials;
import org.example.utilities.CollectionManager;
import org.example.utilities.DBmanager;
import org.springframework.stereotype.Component;

/**
 * Класс, обрабатывающий команды.
 */
@Component
public class CommandHandler {
    /**
     * Команды
     */
    private HashMap<String, Command> commands = new HashMap<>();

    /**
     * Создает экземпляр стандартного обработчика команд с заданными менеджерами коллекций и пользователей.
     *
     * @param cm менеджер коллекций.
     * @param users менеджер пользователей.
     * @return экземпляр стандартного обработчика команд.
     */
    public static CommandHandler standardCommandHandler(CollectionManager cm, DBmanager users) {
        CommandHandler ch = new CommandHandler();
        ch.addCommand(new Add(cm));
        ch.addCommand(new Info(cm));
        ch.addCommand(new Show(cm));
        ch.addCommand(new AddIfMax(cm));
        ch.addCommand(new Remove(cm));
        ch.addCommand(new Update(cm));
        ch.addCommand(new AddIfMin(cm));
        ch.addCommand(new Clear(cm));
        ch.addCommand(new PrintFieldAscending(cm));
        ch.addCommand(new RemoveFirst(cm));
        ch.addCommand(new FilterStartsWithName(cm));
        ch.addCommand(new FilterLessThanTimezone(cm));
        ch.addCommand(new ExecuteScript());
        ch.addCommand(new Register(users));
        ch.addCommand(new Login(users));

        return ch;
    }

    /**
     * Обрабатывает строку команды и возвращает запрос.
     *
     * @param commandString строка команды.
     * @param io интерфейс ввода-вывода для взаимодействия с пользователем.
     * @param auth информация об аутентификации пользователя.
     * @return запрос, сформированный на основе строки команды.
     * @throws CommandNotFoundException выбрасывается, если команда не найдена.
     * @throws CommandArgumentException выбрасывается, если команда содержит неверные аргументы.
     */
    public Request handleString(String commandString, BasicUserIO io, AuthCredentials auth) throws CommandNotFoundException, CommandArgumentException {
        String[] commandArgs = commandString.trim().split("\\s+");

        Command command = commands.get(commandArgs[0]);

        if (command == null) {
            throw new CommandNotFoundException(commandArgs[0]);
        }

        RequestBody body = command.packageBody(Arrays.copyOfRange(commandArgs, 1, commandArgs.length), io);
        if (body == null) {
            return null;
        }
        Request request = new Request(command.getName(), body, auth);
        return request;
    }

    /**
     * Обрабатывает запрос и возвращает ответ.
     *
     * @param request запрос, который требуется обработать.
     * @param users   менеджер пользователей.
     * @return ответ на запрос.
     */
    public Response handleRequest(Request request, DBmanager users) {
        if (commands.get(request.getCommandName()) != null) {
            Integer userId = users.authenticate(request.getAuth());

            if (userId == null && commands.get(request.getCommandName()).requiresAuth()) {
                return new ResponseWithException(new UnauthenticatedException());
            }

            try {
                return commands.get(request.getCommandName()).execute(request);
            } catch (InvalidRequestException e) {
                return new ResponseWithException(e);
            }
        }

        return new ResponseWithException(new CommandNotFoundException(request.getCommandName()));
    }

    /**
     * Добавляет команду в обработчик.
     *
     * @param command команда для добавления.
     */
    public void addCommand(Command command) {
        commands.put(command.getName(), command);
    }

    /**
     * Возвращает список команд, доступных в обработчике.
     *
     * @return список команд.
     */
    public Map<String, Command> getCommands() {
        return this.commands;
    }
}