package org.example.models;

import org.example.utilities.Validatable;

/**
 * Класс, представляющий человека
 * Содержит информацию о имени, возрасте и росте человека
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Human implements Validatable {
    /**
     * Имя человека, не может быть null или пустой строкой
     */
    private String name;

    /**
     * Возраст человека, должен быть больше 0
     */
    private Long age;

    /**
     * Рост человека, должен быть больше 0
     */
    private Integer height;

    /**
     * Конструктор
     *
     */

    public Human(String name, Long age, Integer height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    public Human(String data) {
        try {
            this.name = data.split(" ; ")[0];
            try {
                this.age = (data.split(" ; ")[1].equals("null") ? null : Long.parseLong(data.split(" ; ")[1]));
            } catch (NumberFormatException e) {
                return;
            }

            try {
                this.height = (data.split(" ; ")[2].equals("null") ? null : Integer.parseInt(data.split(" ; ")[2]));
            } catch (NumberFormatException e) {
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }


    /**
     * Возвращает имя человека
     *
     * @return имя человека
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает строковое представление объекта Human в формате name,age,height
     *
     * @return строковое представление человека
     */
    @Override
    public String toString() {
        return name + "," + age + "," + height;
    }

    /**
     * Проверяет корректность данных человека
     *
     * @return true, если имя не null и не пустое, возраст и рост больше 0, иначе false
     */
    @Override
    public boolean validate() {
        if (name == null || name.isEmpty()) return false;
        if (age <= 0) return false;
        if (height <= 0) return false;

        return true;
    }
}