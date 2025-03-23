package org.example.interfaces;

import org.example.utilities.ExecutionResponse;

/**
 * Интерфейс, реализующий исполнение
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public interface Executable {
    /**
     * Выполняет команду с указанными аргументами
     *
     * @param arguments аргументы команды
     * @return результат выполнения команды
     */
    ExecutionResponse apply(String[] arguments);
}
