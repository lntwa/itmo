package org.example.models;

import org.example.exceptions.InvalidFieldException;
import org.example.utilities.AbstractValidator;
import org.example.utilities.Validatable;

import javax.xml.validation.Validator;
import java.io.Serializable;
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
public class City implements Comparable<City>, Serializable {

    /**
     * Валидатор
     */
    public static final Validator VALIDATOR = new Validator();

    /**
     * Уникальный ID города. Должен быть больше 0
     */
    private Integer id;

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

    private String owner = null;

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
                long metersAboveSeaLevel, Double timezone, Float populationDensity, StandardOfLiving standardOfLiving, Human governor) throws InvalidFieldException {
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
        VALIDATOR.validate(this);
    }

    public City() {}

    /**
     * Возвращает уникальный ID города
     *
     * @return ID города
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public static City fromArray(String[] data, String owner) {
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
        list.add(e.getOwner());
        list.add(e.getStandardOfLiving().toString());
        list.add(e.getGovernor().toString());
        return list.toArray(new String[0]);
    }

    public static class Validator implements AbstractValidator<City> {

        /**
         * Проверяет валидность города.
         *
         * @param city город для проверки
         * @throws InvalidFieldException если поле города недопустимо
         */
        public void validate(City city) throws InvalidFieldException {
            validateId(city.getId());
            validateName(city.getName());
            validateCoordinates(city.getCoordinates());
            validateCreationDate(city.getCreationDate());
            validateArea(city.getArea());
            validatePopulation(city.getPopulation());
            validateMetersAboveSeaLevel(city.getMetersAboveSeaLevel());
            validateTimezone(city.getTimezone());
            validatePopulationDensity(city.getPopulationDensity());
            validateStandardOfLiving(city.getStandardOfLiving());
            validateGovernor(city.getGovernor());
        }

        /**
         * Проверяет валидность идентификатора города.
         *
         * @param id идентификатор города
         * @throws InvalidFieldException если идентификатор недопустим
         */
        public void validateId(int id) throws InvalidFieldException {
            if (id <= 0) {
                throw new InvalidFieldException("идентификатор города должен быть больше 0", "idGreaterThan0");
            }
        }

        /**
         * Проверяет валидность названия города.
         *
         * @param name название города
         * @throws InvalidFieldException если название недопустимо
         */
        public void validateName(String name) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(name, "название города не может быть пустым", "cityNameNotEmpty");
            if (name.isEmpty()) {
                throw new InvalidFieldException("название города не может быть пустой строкой", "cityNameNotEmpty");
            }
        }

        /**
         * Проверяет валидность координат города.
         *
         * @param coordinates координаты города
         * @throws InvalidFieldException если координаты недопустимы
         */
        public void validateCoordinates(Coordinates coordinates) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(coordinates, "координаты города не могут быть пустыми", null);
        }

        /**
         * Проверяет валидность даты создания города.
         *
         * @param creationDate дата создания города
         * @throws InvalidFieldException если дата недопустима
         */
        public void validateCreationDate(LocalDate creationDate) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(creationDate, "дата создания города не может быть нулевой", null);
        }

        /**
         * Проверяет валидность площади города.
         *
         * @param area площадь города
         * @throws InvalidFieldException если площадь недопустима
         */
        public void validateArea(Double area) throws InvalidFieldException {
            if (area == null || area <= 0) {
                throw new InvalidFieldException("площадь города должна быть больше 0", "areaGreaterThan0");
            }
        }

        /**
         * Проверяет валидность населения города.
         *
         * @param population население города
         * @throws InvalidFieldException если население недопустимо
         */
        public void validatePopulation(Integer population) throws InvalidFieldException {
            if (population == null || population <= 0) {
                throw new InvalidFieldException("население города должно быть больше 0", "populationGreaterThan0");
            }
        }

        /**
         * Проверяет валидность высоты над уровнем моря.
         *
         * @param meters высота над уровнем моря
         * @throws InvalidFieldException если высота недопустима
         */
        public void validateMetersAboveSeaLevel(long meters) throws InvalidFieldException {
            // Можно добавить дополнительные проверки при необходимости
        }

        /**
         * Проверяет валидность часового пояса.
         *
         * @param timezone часовой пояс
         * @throws InvalidFieldException если часовой пояс недопустим
         */
        public void validateTimezone(Double timezone) throws InvalidFieldException {
            if (timezone == null || timezone <= -13 || timezone > 15) {
                throw new InvalidFieldException("часовой пояс должен быть в диапазоне от -12 до +15", "invalidTimezone");
            }
        }

        /**
         * Проверяет валидность плотности населения.
         *
         * @param density плотность населения
         * @throws InvalidFieldException если плотность недопустима
         */
        public void validatePopulationDensity(Float density) throws InvalidFieldException {
            if (density == null || density <= 0) {
                throw new InvalidFieldException("плотность населения должна быть больше 0", "densityGreaterThan0");
            }
        }

        /**
         * Проверяет валидность уровня жизни.
         *
         * @param standard уровень жизни
         * @throws InvalidFieldException если уровень жизни недопустим
         */
        public void validateStandardOfLiving(StandardOfLiving standard) throws InvalidFieldException {
            AbstractValidator.ensureNotNull(standard, "уровень жизни не может быть пустым", null);
        }

        /**
         * Проверяет валидность данных губернатора.
         *
         * @param governor губернатор города
         * @throws InvalidFieldException если данные губернатора недопустимы
         */
        public void validateGovernor(Human governor) throws InvalidFieldException {
            if (governor != null) { // Губернатор может быть null
                AbstractValidator.ensureNotNull(governor.getName(), "имя губернатора не может быть пустым", null);
                if (governor.getAge() != null && governor.getAge() <= 0) {
                    throw new InvalidFieldException("возраст губернатора должен быть больше 0", "governorAgeGreaterThan0");
                }
                if (governor.getHeight() != null && governor.getHeight() <= 0) {
                    throw new InvalidFieldException("рост губернатора должен быть больше 0", "governorHeightGreaterThan0");
                }
            }
        }
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

    public String toStr() {
        StringBuilder sb = new StringBuilder();

        sb.append(id != null ? id : "0").append(",");

        sb.append(name != null ? name : "").append(",");

        sb.append(coordinates != null ? coordinates.toString() : "0,0").append(",");

        sb.append(creationDate != null ? creationDate.toString() : "").append(",");

        sb.append(area).append(",");
        sb.append(population).append(",");
        sb.append(metersAboveSeaLevel).append(",");
        sb.append(timezone).append(",");
        sb.append(populationDensity).append(",");

        sb.append(standardOfLiving != null ? standardOfLiving.toString() : "").append(",");

        sb.append(governor != null ? governor.toString() : "");

        return sb.toString();
    }
}