package org.example;

import org.example.commands.*;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.managers.DumpManager;
import org.example.managers.NetworkManager;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    public static final Logger logger = Logger.getLogger(Server.class.getName());
    static String[] userCommand = new String[2];
    static byte[] arr = new byte[5069];
    static int len = arr.length;

    public static void main(String[] args) {
        System.setProperty("file.encoding", "CP866");
        try {
            System.setOut(new PrintStream(System.out, true, "CP866"));
            System.setErr(new PrintStream(System.err, true, "CP866"));

            Logger rootLogger = Logger.getLogger("");
            for (Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }

            ConsoleHandler handler = new ConsoleHandler();
            handler.setEncoding("CP866");
            handler.setLevel(Level.ALL);
            rootLogger.addHandler(handler);
            rootLogger.setLevel(Level.ALL);
        } catch (UnsupportedEncodingException ignored) {}


        var console = new Console();
        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            logger.info("Не введено название файла. Сервер не был запушен.");
            System.exit(1);
        }
        logger.info("Сервер успешно запущен!");
        var dumpManager = new DumpManager(args[0], console);
        var collectionManager = new CollectionManager(dumpManager);

        if (!collectionManager.loadCollection()) {
            logger.info("Не удалось загрузить коллекцию.");
            System.exit(1);

        }
        var networkManager = new NetworkManager(14775, 800);
        while (!networkManager.init()) {
            logger.info("Менеджер сетевого взаимодействия инициализирован!");
        }
        collectionManager.sort();
        var commandManager = new CommandManager();
        commandManager.register(CommandTypes.Add.Type(), new Add(console, collectionManager));
        commandManager.register(CommandTypes.AddIfMin.Type(), new AddIfMin(console, collectionManager));
        commandManager.register(CommandTypes.AddIfMax.Type(), new AddIfMax(console, collectionManager));
        commandManager.register(CommandTypes.Clear.Type(), new Clear(console, collectionManager));
        commandManager.register(CommandTypes.FilterLessThanTimezone.Type(), new FilterLessThanTimezone(console, collectionManager));
        commandManager.register(CommandTypes.FilterStartsWithName.Type(), new FilterStartsWithName(console, collectionManager));
        commandManager.register(CommandTypes.Info.Type(), new Info(collectionManager));
        commandManager.register(CommandTypes.PrintFieldAscending.Type(), new PrintFieldAscending(console, collectionManager));
        commandManager.register(CommandTypes.Remove.Type(), new Remove(console, collectionManager));
        commandManager.register(CommandTypes.RemoveFirst.Type(), new RemoveFirst(console, collectionManager));
        commandManager.register(CommandTypes.Show.Type(), new Show(console, collectionManager));
        commandManager.register(CommandTypes.Update.Type(), new Update(console, collectionManager));
        commandManager.register(CommandTypes.ExecuteScript.Type(), new ExecuteScript(console));

        run(networkManager, console, commandManager);
    }

    public static void run(NetworkManager networkManager, Console console, CommandManager commandManager) {
        while (true) {
            arr = networkManager.receiveData(len);
            Container commandd = NetworkManager.deserialize(arr);
            if (commandd != null) {
                userCommand[0] = commandd.getCommandType().Type();
                userCommand[1] = commandd.getArgs();
                var command = commandManager.getCommands().get(userCommand[0]);
                ExecutionResponse response;
                if (userCommand[0].equals("")) response = new ExecutionResponse("");
                if (command == null)
                    response = new ExecutionResponse(false, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");
                else response = command.apply(userCommand);
                logger.info("Команда обработана!");
                networkManager.sendData(NetworkManager.serializer(response));
                logger.info("Отправлен ответ клиенту!");
            }

        }
    }
}