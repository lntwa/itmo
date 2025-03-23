package org.example.managers;

import org.example.interfaces.Sortable;
import org.example.model.City;
import org.example.utilities.Console;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.*;
import org.example.utilities.Console;

/**
 * Класс менеджера коллекции, управляющий коллекцией городов
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class CollectionManager implements Sortable {
    /**
     * Основная коллекция
     */
    private ArrayDeque<City> collection = new ArrayDeque<>();
    /**
     * Время последней инициализации коллекции
     */
    private LocalDateTime lastInitializationTime;
    /**
     * Время последнего сохранения коллекции
     */
    private LocalDateTime lastSaveTime;
    /**
     * Менеджер для сериализации и десериализации коллекции
     */
    private DumpManager dumpManager;
    /**
     * Консоль
     */
    private Console console = new Console();

    /**
     * Конструктор
     * @param dumpManager менеджер для сериализации и десериализации коллекции
     */
    public CollectionManager (DumpManager dumpManager) {
        this.lastInitializationTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
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
     * Сохраняет коллекцию в файл с помощью dumpManager
     */
    public void saveCollection() {
        dumpManager.writeToCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Загружает коллекцию из файла с помощью dumpManager
     * @return true, если коллекция успешно загрузилась, иначе false
     */
    public boolean loadCollection() {
        collection.clear();
        dumpManager.readCollection(collection);
        lastInitializationTime = LocalDateTime.now();
        if (!areIdsUnique()) {
            console.printError("Найдены дубликаты ID в коллекции!");
            collection.clear();
        }
        if (!collection.isEmpty()) {
            sort();
        }
        return true;
    }

    /**
     * Генерирует новый уникальный ID для элемента коллекции
     * @return новый уникальный ID
     */
    public Integer generateNewId() {
        int id = collection.stream().mapToInt(City::getId).max().orElse(0);
        return id + 1;
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
    public ArrayDeque<City> getCollection() {
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
        int length = collection.size();
        City[] array = new City[length];
        Iterator<City> iterator = collection.iterator();
        for (int i = 0; i < length; i++) {
            array[i] = iterator.next();
        }
        Arrays.sort(array);
        collection = new ArrayDeque<>(Arrays.asList(array));
    }
    /**
     * Возвращает строковое представление коллекции.
     *
     * @return строковое представление коллекции
     */
    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        StringBuilder info = new StringBuilder();
        for (City element : collection) {
            info.append(element).append("\n\n");
        }
        return info.toString().trim();
    }
}
