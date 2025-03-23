package org.example.interfaces;

/**
 * Интерфейс, реализующий проверку правильности вводимых данных
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public interface Validatable {
    /**
     * Проверяет корректность данных объекта
     *
     * @return true, если данные корректны, иначе false
     */
    boolean validate();
}
