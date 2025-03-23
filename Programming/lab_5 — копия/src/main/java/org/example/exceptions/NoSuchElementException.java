package org.example.exceptions;

/**
 * Обработка исключения на пустоту файла
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class NoSuchElementException extends RuntimeException {
    @Override
    public String toString() {
        return "Файл пуст!";
    }
}
