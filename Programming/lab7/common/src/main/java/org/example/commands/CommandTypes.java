package org.example.commands;

import java.io.Serializable;

public enum CommandTypes implements Serializable {

    Add("add"),
    AddIfMin("add_if_min"),
    AddIfMax("add_if_max"),
    Clear("clear"),
    FilterLessThanTimezone("filter_less_than_timezone"),
    FilterStartsWithName("filter_starts_with_name"),
    Help("help"),
    Exit("exit"),
    Info("info"),
    PrintFieldAscending("print_field_ascending"),
    Remove("remove"),
    RemoveFirst("remove_first"),
    Save("save"),
    Show("show"),
    Update("update"),
    Register("register"),
    Login("login"),
    ExecuteScript("execute_script");
    private final String type;

    private CommandTypes(String type) {
        this.type = type;
    }

    public String Type() {
        return type;
    }

    private static final long serialVersionUID = 14L;

    public static CommandTypes getByString(String string) {
        if (string == null || string.trim().isEmpty()) {
            return null;
        }

        String normalized = string.trim().toLowerCase().replace(' ', '_');

        for (CommandTypes cmdType : CommandTypes.values()) {
            if (cmdType.type.equals(normalized)) {
                return cmdType;
            }
        }

        return null;
    }
}