package org.example.network;
import org.example.models.City;

/**
 * Класс, представляющий ответ на запрос с информацией о городах.
 */
public class ResponseWithCities extends Response {
    /**
     * Города
     */
    private City[] cities;

    /**
     * Создает новый объект ответа с информацией о городах.
     *
     * @param cities массив городов.
     */
    public ResponseWithCities(City[] cities) {
        this.cities = cities;
    }

    /**
     * Возвращает город по индексу.
     *
     * @param i индекс города.
     * @return город.
     */
    public City getCity(int i) {
        return cities[i];
    }

    /**
     * Возвращает количество городов.
     *
     * @return количество городов.
     */
    public int getCitiesCount() {
        return cities.length;
    }

    /**
     * Возвращает массив городов.
     *
     * @return массив городов.
     */
    public City[] getCities() {
        return cities;
    }
}