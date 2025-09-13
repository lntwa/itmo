package org.example.utilities;
import org.example.models.City;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Predicate;

/**
 * Менеджер коллекции организаций.
 */
@Component
public interface CollectionManager {

    /**
     * Возвращает текущую коллекцию организаций.
     *
     * @return текущая коллекция организаций.
     */
    ConcurrentLinkedDeque<City> getCollection();

    /**
     * Возвращает организацию по ее идентификатору.
     *
     * @param id идентификатор организации.
     * @return организация с указанным идентификатором или null, если организация не найдена.
     */
    City getItemById(int id);

    /**
     * Добавляет новую организацию в коллекцию.
     *
     * @param city организация для добавления.
     * @return идентификатор добавленной организации.
     */
    int add(City city);

    Integer authenticate(AuthCredentials auth);

    /**
     * Обновляет информацию об организации в коллекции.
     *
     * @param city организация для обновления.
     * @return true, если обновление прошло успешно, иначе false.
     */
    boolean update(int id, City city);

    /**
     * Удаляет организацию из коллекции по ее идентификатору.
     *
     * @param id идентификатор организации для удаления.
     */
    void remove(int id);

    /**
     * Удаляет все организации, удовлетворяющие заданному условию.
     *
     * @param predicate условие для удаления организации.
     * @return количество удаленных организаций.
     */
    int removeIf(Predicate<? super City> predicate);

}