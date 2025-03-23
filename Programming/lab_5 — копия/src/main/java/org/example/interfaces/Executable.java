package org.example.interfaces;

import org.example.utilities.ExecutionResponse;

/**
 * Интерфейс, реализующий исполнение
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public interface Executable {
    ExecutionResponse apply(String[] arguments);
}
