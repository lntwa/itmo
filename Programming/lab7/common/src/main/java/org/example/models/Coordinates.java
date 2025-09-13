package org.example.models;

import org.example.utilities.Validatable;

import java.io.Serializable;

/**
 * Класс, представляющий координаты
 * Координаты состоят из двух полей: x и y, где x не может превышать 330, а y не может быть null
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Coordinates implements Validatable, Serializable {
    /**
     * Координата x, максимальное значение 330
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
    public Coordinates(double x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(String data) {
        try {
            try {
                this.x = Double.parseDouble(data.split(";")[0]);
            } catch (NumberFormatException e) {
            }
            try {
                this.y = Long.parseLong(data.split(";")[1]);
            } catch (NumberFormatException e) {
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public double getX() {
        return x;
    }

    public Long getY() {
        return y;
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
     * @return true, если данные корректны, иначе false
     */
    @Override
    public boolean validate() {
        return (x <= 330) && (y != null);
    }
}
