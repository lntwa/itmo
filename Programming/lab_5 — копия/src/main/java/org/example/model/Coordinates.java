package org.example.model;

import org.example.interfaces.Validatable;

/**
 * Класс, представляющий координаты.
 * Координаты состоят из двух полей: x и y, где x не может превышать 330, а y не может быть null
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Coordinates implements Validatable {
    /**
     * Координата x, максимальное значение: 330
     */
    private double x;

    /**
     * Координата y, не может быть null
     */
    private Long y;

    /**
     * Конструктор
     *
     * @param x координата x
     * @param y координата y
     */
    public Coordinates (double x, Long y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Возвращает строковое представление координат в формате x,y
     *
     * @return строковое представление координат
     */
    @Override
    public String toString() {
        return x + "," + y;
    }

    /**
     * Проверяет корректность данных координат
     *
     * @return true, если (x <= 330 и y != null), иначе false
     */
    @Override
    public boolean validate() {
        return (x <= 330) && (y != null);
    }
}
