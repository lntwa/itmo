package org.example;

import org.example.commands.*;
import org.example.managers.*;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;
import org.example.utilities.Record;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Server {
    public static final Logger logger = Logger.getLogger(Server.class.getName());
    static String[] userCommand = new String[2];
    static byte[] arr = new byte[65536];
    static int len = arr.length;
    private static ExecutorService readingPool = new ForkJoinPool(1000);
    private static ExecutorService sendPool = Executors.newFixedThreadPool(100);

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
        logger.info("Сервер успешно запущен!");

        DBmanager dbmanager = new DBmanager();
        dbmanager.connect();
        var collectionManager = new CollectionManager(dbmanager);

        if (!collectionManager.loadCollection()) {
            logger.severe("Не удалось загрузить коллекцию.");
            System.exit(1);
        }

        var networkManager = new NetworkManager(14775, 800);
        while (!networkManager.init()) {
        }
        logger.info("Менеджер сетевого взаимодействия инициализирован!");
        logger.info("Сервер успешно запущен!");
        collectionManager.sort();
        var commandManager = new CommandManager();
        commandManager.register(CommandTypes.Add.Type(), new Add(console, collectionManager, dbmanager));
        commandManager.register(CommandTypes.AddIfMin.Type(), new AddIfMin(console, collectionManager, dbmanager));
        commandManager.register(CommandTypes.AddIfMax.Type(), new AddIfMax(console, collectionManager, dbmanager));
        commandManager.register(CommandTypes.Clear.Type(), new Clear(console, collectionManager, dbmanager));
        commandManager.register(CommandTypes.FilterLessThanTimezone.Type(), new FilterLessThanTimezone(console, collectionManager));
        commandManager.register(CommandTypes.FilterStartsWithName.Type(), new FilterStartsWithName(console, collectionManager));
        commandManager.register(CommandTypes.Info.Type(), new Info(collectionManager));
        commandManager.register(CommandTypes.PrintFieldAscending.Type(), new PrintFieldAscending(console, collectionManager));
        commandManager.register(CommandTypes.Remove.Type(), new Remove(console, collectionManager, dbmanager));
        commandManager.register(CommandTypes.RemoveFirst.Type(), new RemoveFirst(console, collectionManager, dbmanager));
        commandManager.register(CommandTypes.Show.Type(), new Show(console, collectionManager, dbmanager));
        commandManager.register(CommandTypes.Update.Type(), new Update(console, collectionManager, dbmanager));
        commandManager.register(CommandTypes.ExecuteScript.Type(), new ExecuteScript(console));
        commandManager.register(CommandTypes.Register.Type(), new Register(dbmanager));
        commandManager.register((CommandTypes.Login.Type()), new Login(dbmanager));

        run(networkManager, commandManager, dbmanager);
    }

    public static void run(NetworkManager networkManager, CommandManager commandManager, DBmanager dbManager) {
        while (true) {
            Record rec = networkManager.receiveData(len);
            if (rec != null) {
                readingPool.submit(() -> {
                    Container commandd = NetworkManager.deserialize(rec.getArr());
                    if (commandd != null) {
                        execute(commandd, rec.getAddr(), commandManager, networkManager, dbManager);
                    }
                });
            }
        }
    }

    public static void execute(Container commandd, SocketAddress address, CommandManager
            commandManager, NetworkManager networkManager, DBmanager dbManager) {
        Runnable runnable =
                () -> {
                    userCommand[0] = commandd.getCommandType().Type();
                    userCommand[1] = commandd.getArgs();
                    var command = commandManager.getCommands().get(userCommand[0]);
                    ExecutionResponse response;
                    if (userCommand[0].equals("")) response = new ExecutionResponse("");
                    if (command == null)
                        response = new ExecutionResponse(false, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");
                    else {
                        if (!userCommand[0].equals("login") & !userCommand[0].equals("register")) {
                            if (dbManager.exists(commandd.getLogin(), commandd.getPassword())) {
                                response = command.apply(userCommand, commandd.getLogin());
                            } else response = new ExecutionResponse(false, "Неверный логин или пароль");
                        } else {
                            response = command.apply(userCommand, commandd.getLogin());
                        }

                    }
                    logger.info("Команда обработана!");
                    if (response != null) {
                        sendData(response, address, networkManager);
                    } else {
                        sendData(new ExecutionResponse(false, "Не удалось выполнить команду"), address, networkManager);
                    }
                };
        new Thread(runnable).start();
    }


    public static void sendData(ExecutionResponse response, SocketAddress address, NetworkManager networkManager) {
        sendPool.submit(() -> {
            byte[] bytes = NetworkManager.serializer(response);
            networkManager.sendData(new Record(bytes, address));
            logger.info("Отправлен ответ серверу!");
        });
    }
}