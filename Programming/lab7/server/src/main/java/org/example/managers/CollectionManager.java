package org.example.managers;

import org.example.models.City;
import org.example.utilities.Console;
import org.example.utilities.Sortable;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Класс менеджера коллекции, управляющий коллекцией городов
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class CollectionManager implements Sortable {

    private int nextId = 1;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Время последней инициализации коллекции
     */
    private LocalDateTime lastInitializationTime;
    /**
     * Время последнего сохранения коллекции
     */
    private LocalDateTime lastSaveTime;
    private ConcurrentSkipListMap<Integer, City> cities = new ConcurrentSkipListMap<>();
    /**
     * хранимая коллекция
     */
    private ConcurrentLinkedDeque<City> collection = new ConcurrentLinkedDeque<>();
    /**
     * Консоль
     */
    private Console console = new Console();

    private final DBmanager dBmanager;

    public CollectionManager(DBmanager dBmanager) {
        this.lastInitializationTime = null;
        this.lastSaveTime = null;
        this.dBmanager = dBmanager;
    }

    /**
     * Возвращает время последней инициализации коллекции
     * @return время последней инициализации
     */
    public LocalDateTime getLastInitializationTime() {
        return lastInitializationTime;
    }

    /**
     * Возвращает время последнего сохранения коллекции
     * @return время последнего сохранения
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }


    /**
     * Загружает коллекцию с помощью dbmanager
     * @return true, если коллекция успешно загрузилась, иначе false
     */
    public boolean loadCollection() {
        lock.readLock().lock();
        collection.clear();
        dBmanager.loadCollection(collection);
        lastInitializationTime = LocalDateTime.now();
        for (City city : collection)
            if (getById(city.getId()) != null) {
                collection.clear();
                return false;
            } else {
                if (city.getId() > nextId) nextId = city.getId();
                cities.put(city.getId(), city);
            }
        sort();
        lock.readLock().unlock();
        return true;
    }

    public ConcurrentLinkedDeque<City> getCollectionFromDB() {
        return dBmanager.getCities();
    }


    /**
     * Возвращает город по его ID
     * @param id уникальный ID
     * @return город по ID
     */
    public City getById(int id) {
        for (City city : collection) {
            if (city.getId() == id) {
                return city;
            }
        }
        return null;
    }

    public City getFirst() {
        return collection.stream().limit(1).toList().get(0);
    }


    /**
     * Проверяет уникальность ID в коллекции
     * @return true, если все ID уникальны, иначе false
     */
    public boolean areIdsUnique() {
        Set<Integer> idSet = new HashSet<>();
        for (City city : collection) {
            if (!idSet.add(city.getId())) { // Если add возвращает false, ID уже существует
                return false; // Найден дубликат
            }
        }
        return true; // Все ID уникальны
    }

    /**
     * Проверяет, содержится ли указанный город в коллекции
     * @param city указанный город
     * @return true, если город содержится в коллекции, иначе false
     */
    public boolean isContain(City city) {
        return getById(city.getId()) != null;
    }

    /**
     * Возвращает основную коллекцию городов
     * @return коллекцию городов
     */
    public ConcurrentLinkedDeque<City> getCollection() {
        return this.collection;
    }

    /**
     * Добавляет город в коллекцию, если его ID уникален
     * @param city город для добавления
     * @return true, если город успешно добавлен, иначе false
     */
    public boolean add(City city) {
        if (isContain(city)) {
            return false;
        }
        collection.add(city);
        sort();
        return true;
    }

    /**
     * Удаляет город из коллекции по его ID
     * @param id ID города для удаления
     * @return  true, если город успешно удален, иначе false
     */
    public boolean remove(int id) {
        City element = getById(id);
        if (element == null) {
            return false;
        }
        collection.remove(element);
        sort();
        return true;
    }

    /**
     * Удаляет первый город из коллекции, если она не пуста
     */
    public void removeFirst() {
        if (!collection.isEmpty()) {
            collection.pollFirst();
        }
    }
    /**
     * Сортирует коллекцию городов
     */
    @Override
    public void sort() {
        collection = collection.stream().
                sorted().collect(Collectors.toCollection(ConcurrentLinkedDeque::new));
    }
    /**
     * Возвращает строковое представление коллекции.
     *
     * @return строковое представление коллекции
     */
    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        System.out.println(collection.size());
        return collection.stream().map(Objects::toString).collect(Collectors.joining ("\n\n"));
    }
}
