package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.utilities.Console;
import org.example.utilities.ExecutionResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Класс команды, которая выводит информацию о коллекции
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Info extends Command {
    /**
     * Менеджер коллекции
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор
     *
     * @param collectionManager менеджер коллекции
     */
    public Info(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнение команды
     *
     * @param arguments массив с аргументами команды
     * @return возвращает информацию о выполнении команды
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        LocalDateTime lastInitTime = collectionManager.getLastInitializationTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

        var s = "Сведения о коллекции:\n";
        s += " Тип: " + collectionManager.getCollection().getClass().toString() + "\n";
        s += " Количество элементов: " + collectionManager.getCollection().size() + "\n";
        s += " Дата последнего сохранения: " + lastSaveTimeString + "\n";
        s += " Дата последней инициализации: " + lastInitTimeString;
        return new ExecutionResponse(s);
    }
}
