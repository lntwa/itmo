package org.example.commands;

import org.example.managers.DBmanager;
import org.example.utilities.ExecutionResponse;

public class Login extends Command {
    private final DBmanager dbManager;

    public Login(DBmanager dbManager) {
        super("login", "авторизация");
        this.dbManager = dbManager;
    }

    @Override
    public ExecutionResponse apply(String[] arguments, String login) {
        var arg = arguments[1].split(";");
        if (dbManager.exists(arg[0], arg[1])) {
            return new ExecutionResponse(true, "Вы успешно авторизованы!");
        } else return new ExecutionResponse(false, "Неверные логин или пароль!");
    }
}
