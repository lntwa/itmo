package org.example.model;

import org.example.interfaces.Validatable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Класс, представляющий город
 *
 * Содержит информацию о городе: ID, название, координаты, дата создания,
 * площадь, население, высота над уровнем моря, часовой пояс, плотность населения,
 * уровень жизни и губернатор.
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class City implements Comparable<City>, Validatable {
    /**
     * Уникальный ID города. Должен быть больше 0
     */
    private int id;

    /**
     * Название города. Не может быть null или пустой строкой
     */
    private String name;

    /**
     * Координаты города. Не могут быть null
     */
    private Coordinates coordinates;

    /**
     * Дата создания записи о городе. Не может быть null. Генерируется автоматически
     */
    private LocalDate creationDate;

    /**
     * Площадь города. Должна быть больше 0. Не может быть null
     */
    private Double area;

    /**
     * Население города. Должно быть больше 0. Не может быть null
     */
    private Integer population;

    /**
     * Высота города над уровнем моря
     */
    private long metersAboveSeaLevel;

    /**
     * Часовой пояс города. Должен быть больше -13 и не превышать 15
     */
    private Double timezone;

    /**
     * Плотность населения города. Должна быть больше 0
     */
    private Float populationDensity;

    /**
     * Уровень жизни в городе. Может быть null
     */
    private StandardOfLiving standardOfLiving;

    /**
     * Губернатор города. Не может быть null
     */
    private Human governor;

    /**
     * Конструктор
     *
     * @param id уникальный ID города
     * @param name название города
     * @param coordinates координаты города
     * @param creationDate дата создания записи о городе
     * @param area площадь города
     * @param population население города
     * @param metersAboveSeaLevel высота над уровнем моря
     * @param timezone часовой пояс города
     * @param populationDensity плотность населения города
     * @param standardOfLiving уровень жизни в городе
     * @param governor губернатор города
     */
    public City(int id, String name, Coordinates coordinates, LocalDate creationDate, Double area, Integer population,
                long metersAboveSeaLevel, Double timezone, Float populationDensity, StandardOfLiving standardOfLiving, Human governor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.timezone = timezone;
        this.populationDensity = populationDensity;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }

    /**
     * Возвращает уникальный ID города
     *
     * @return ID города
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает дату создания записи о городе
     *
     * @return дата создания
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает название города
     *
     * @return название города
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает координаты города
     *
     * @return координаты города
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает площадь города
     *
     * @return площадь города
     */
    public Double getArea() {
        return area;
    }

    /**
     * Возвращает население города
     *
     * @return население города
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     * Возвращает высоту города над уровнем моря
     *
     * @return высота над уровнем моря
     */
    public long getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    /**
     * Возвращает часовой пояс города
     *
     * @return часовой пояс города
     */
    public Double getTimezone() {
        return timezone;
    }

    /**
     * Возвращает плотность населения города
     *
     * @return плотность населения города
     */
    public Float getPopulationDensity() {
        return populationDensity;
    }

    /**
     * Возвращает уровень жизни в городе
     *
     * @return уровень жизни в городе
     */
    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    /**
     * Возвращает губернатора города
     *
     * @return губернатор города
     */
    public Human getGovernor() {
        return governor;
    }

    /**
     * Создает объект City из массива строк
     *
     * @param data массив строк с данными о городе
     * @return объект City или null, если данные некорректны
     */
    public static City fromArray(String[] data) {
        try {
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            double x = Double.parseDouble(data[2]);
            Long y = Long.parseLong(data[3]);
            LocalDate creationDate = LocalDate.parse(data[4], DateTimeFormatter.ISO_LOCAL_DATE);
            Double area = Double.parseDouble(data[5]);
            Integer population = Integer.parseInt(data[6]);
            long metersAboveSeaLevel = Long.parseLong(data[7]);
            Double timezone = Double.parseDouble(data[8]);
            Float populationDensity = Float.parseFloat(data[9]);
            StandardOfLiving standardOfLiving = (data[10].equals("null") || data[10].isEmpty()) ? null : StandardOfLiving.valueOf(data[10]);
            String nameGovernor = data[11];
            Long age = Long.parseLong(data[12]);
            Integer height = Integer.parseInt(data[13]);
            Human human = new Human(nameGovernor, age, height);
            Coordinates coordinates = new Coordinates(x, y);
            return new City(id, name, coordinates, creationDate, area, population, metersAboveSeaLevel, timezone, populationDensity, standardOfLiving, human);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Преобразует объект City в массив строк
     *
     * @param e объект City
     * @return массив строк с данными о городе
     */
    public static String[] toArray(City e) {
        var list = new ArrayList<String>();
        list.add(Integer.toString(e.getId()));
        list.add(e.getName());
        list.add(e.getCoordinates().toString());
        list.add(e.getCreationDate().toString());
        list.add(Double.toString(e.getArea()));
        list.add(Integer.toString(e.getPopulation()));
        list.add(Long.toString(e.getMetersAboveSeaLevel()));
        list.add(Double.toString(e.getTimezone()));
        list.add(Float.toString(e.getPopulationDensity()));
        list.add(e.getStandardOfLiving().toString());
        list.add(e.getGovernor().toString());
        return list.toArray(new String[0]);
    }

    /**
     * Проверяет корректность данных города
     *
     * @return возвращает true, если данные корректны, иначе false
     */
    @Override
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (area <= 0) return false;
        if (population <= 0) return false;
        if (timezone <= -13 || timezone > 15) return false;
        if (populationDensity <= 0) return false;
        if (governor == null) return false;

        return true;
    }

    /**
     * Сравнивает текущий город с другим городом по ID
     *
     * @param other объект City для сравнения
     * @return результат сравнения (отрицательное число, 0 или положительное число)
     */
    @Override
    public int compareTo(City other) {
        return Integer.compare(this.id, other.id);
    }

    /**
     * Возвращает строковое представление объекта City
     *
     * @return строковое представление города
     */
    @Override
    public String toString() {
        return "City {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", coordinates = " + coordinates +
                ", creationDate = " + creationDate +
                ", area = " + area +
                ", population = " + population +
                ", metersAboveSeaLevel = " + metersAboveSeaLevel +
                ", timezone = " + timezone +
                ", populationDensity = " + populationDensity +
                ", standardOfLiving = " + standardOfLiving +
                ", governor = " + governor +
                '}';
    }
}