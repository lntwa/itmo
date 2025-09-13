package org.example.commands;

import org.example.managers.DBmanager;
import org.example.utilities.ExecutionResponse;

public class Register extends Command {
    private final DBmanager dbManager;

    public Register(DBmanager dbManager) {
        super("register", "Регистрация");
        this.dbManager = dbManager;
    }

    @Override
    public ExecutionResponse apply(String[] arguments,String login) {
        var arg = arguments[1].split(";");
        if (dbManager.registerUser(arg[0], arg[1])) {
            return new ExecutionResponse(true, "Вы успешно зарегистрированы!");
        } else return new ExecutionResponse(false, "Такой пользователь уже существует!");
    }
}
