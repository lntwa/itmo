package org.example.network;

import org.example.models.City;

/**
 * Класс, представляющий тело запроса с городом.
 */
public class RequestBodyWithCity extends RequestBody {
    /**
     * город
     */
    private City city;

    /**
     * Создает новый объект тела запроса с городом.
     *
     * @param args аргументы запроса.
     * @param city город.
     */
    public RequestBodyWithCity(String[] args, City city) {
        super(args);
        this.city = city;
    }

    /**
     * Возвращает город.
     *
     * @return город.
     */
    public City getCity() {
        return city;
    }
}