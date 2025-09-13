package org.example;

import org.example.commands.CommandTypes;
import org.example.managers.AskManager;
import org.example.managers.NetworkManager;
import org.example.utilities.Console;
import org.example.utilities.Runner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Client {
    public static void main(String[] args) throws AskManager.AskBreak, IOException {
        var console = new Console();
        NetworkManager networkManager = new NetworkManager(8000);

        while (!networkManager.init(args)) {}
        Map<CommandTypes,String[]> commands = new HashMap<>();
        commands.put(CommandTypes.Add,new String[]{"add", "добавить новый элемент в коллекцию"});
        commands.put(CommandTypes.AddIfMin,new String[]{"add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции"});
        commands.put(CommandTypes.AddIfMax, new String[]{"add_if_max", "добавить новый элемент в коллекцию, если его значение больше, чем у наибольшего элемента этой коллекции"});
        commands.put(CommandTypes.Clear, new String[]{"clear", "очистить коллекцию"});
        commands.put(CommandTypes.Help, new String[]{"help", "вывести справку по доступным командам"});
        commands.put(CommandTypes.FilterLessThanTimezone,new String[]{"filter_less_than_timezone", "вывести элементы, значение поля timezone которых меньше заданного"});
        commands.put(CommandTypes.FilterStartsWithName,new String[]{"filter_starts_with_name", "вывести элементы, значение поля name которых начинается с заданной подстроки"});
        commands.put(CommandTypes.Info,new String[]{"info", "вывести в стандартный поток вывода информацию о коллекции"});
        commands.put(CommandTypes.PrintFieldAscending,new String[]{"print_field_ascending", "вывести значения поля standardOfLiving всех элементов в порядке возрастания"});
        commands.put(CommandTypes.Remove,new String[]{"remove", "удалить элемент из коллекции по его id"});
        commands.put(CommandTypes.Exit,new String[]{"exit","завершить программу (без сохранения в файл)"});
        commands.put(CommandTypes.RemoveFirst,new String[]{"remove_first", "удалить первый элемент из коллекции"});
        commands.put(CommandTypes.Show,new String[]{"show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении"});
        commands.put(CommandTypes.Update,new String[]{"update", "обновить значение элемента коллекции, id которого равен заданному"});
        commands.put(CommandTypes.ExecuteScript,new String[]{"execute_script", "исполнить скрипт из указанного файла"});

        new Runner(networkManager, console, commands).interactiveMode();

    }
}
