package org.example.models;

import org.example.utilities.AbstractValidator;
import org.example.utilities.Validatable;

import javax.xml.validation.Validator;
import java.io.Serializable;
import org.example.exceptions.InvalidFieldException;

/**
 * Класс, представляющий координаты
 * Координаты состоят из двух полей: x и y, где x не может превышать 330, а y не может быть null
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Coordinates implements Serializable {

    /**
     * Валидатор
     */
    public static final Validator VALIDATOR = new Validator();

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
    public Coordinates(double x, Long y) throws InvalidFieldException {
        this.x = x;
        this.y = y;
        VALIDATOR.validate(this);
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
     * Валидатор для объекта координат.
     */
    public static class Validator implements AbstractValidator<Coordinates> {
        /**
         * Проверяет корректность значений координат.
         *
         * @param coordinates объект координат для проверки
         * @throws InvalidFieldException если значения координат некорректны
         */
        public void validate(Coordinates coordinates) throws InvalidFieldException {
            validateX(coordinates.x);
            validateY(coordinates.y);
        }

        /**
         * Проверяет корректность значения координаты X.
         *
         * @param x значение координаты X
         * @throws InvalidFieldException если значение координаты X некорректно
         */
        public void validateX(Double x) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(x, "Поле x не может быть пустым.", "coordinatesXNotNull");
        }

        /**
         * Проверяет корректность значения координаты Y.
         *
         * @param y значение координаты Y
         * @throws InvalidFieldException если значение координаты Y некорректно
         */
        public void validateY(Long y) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(y, "Поле y не может быть пустым.", "coordinatesYNotNull");
        }
    };
}
