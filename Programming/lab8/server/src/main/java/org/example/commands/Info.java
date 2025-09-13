package org.example.commands;

import org.example.exceptions.InvalidRequestException;
import org.example.network.Request;
import org.example.network.Response;
import org.example.utilities.CollectionManager;
import org.example.utilities.TerminalColors;

/**
 * Команда вывода информации
 */
public class Info extends Command {
    /**
     * Менеджер коллекции
     */
    private CollectionManager col;

    /**
     * Конструктор класса.
     *
     * @param col менеджер коллекции
     */
    public Info(CollectionManager col) {
        super("info");

        this.col = col;
    }

    /**
     * Возвращает строку с использованием команды.
     *
     * @return строка использования команды
     */
    public String getUsage() {
        return TerminalColors.colorString("info", TerminalColors.GREEN)
                + " - вывести в стандартный поток вывода информацию о коллекции";
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
        StringBuilder sb = new StringBuilder();
        sb.append("Type: " + col.getCollection().getClass().getSimpleName() + '\n');
        sb.append("Size: " + col.getCollection().size() + '\n');
        return new Response(sb.toString(), "infoResponse", new Object[] {col.getCollection().getClass().getSimpleName(), col.getCollection().size()});
    }
}