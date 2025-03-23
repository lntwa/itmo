package org.example.managers;

import org.example.model.City;
import org.example.model.Coordinates;
import org.example.model.Human;
import org.example.model.StandardOfLiving;
import org.example.utilities.Console;

import java.time.LocalDate;
import java.util.NoSuchElementException;


/**
 * Класс, запрашивающий данные у пользователя
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class AskManager {
    /**
     * Исключение для выхода из режима запроса данных
     */
    public static class AskBreak extends Exception {
    }

    /**
     * Функция запроса данных о городе
     *
     * @param console консоль
     * @param id уникальный номер города
     * @return возвращает город
     * @throws AskBreak исключение для выхода из режима запроса данных
     */
    public static City askCity(Console console, int id) throws AskBreak {
        try {

            String name;
            while (true) {
                console.println("name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
            }

            var coordinates = askCoordinates(console);

            Double area;
            while (true) {
                console.println("area: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        area = Double.parseDouble(line);
                        if (area > 0) break;
                    } catch (NumberFormatException e) {}
                }
            }

            Integer population;
            while (true) {
                console.println("population: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        population = Integer.parseInt(line);
                        if (population > 0) break;
                    } catch (NumberFormatException e) {}
                }
            }

            long metersAboveSeaLevel;
            while (true) {
                console.println("meters above sea level: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    metersAboveSeaLevel = Long.parseLong(line);
                    break;
                }
            }

            Double timezone;
            while (true) {
                console.println("timezone: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        timezone = Double.parseDouble(line);
                        if (timezone > -13 && timezone <= 15) break;
                    } catch (NumberFormatException e) {}
                }
            }

            Float populationDensity;
            while (true) {
                console.println("populationDensity: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        populationDensity = Float.parseFloat(line);
                        if (populationDensity > 0) break;
                    } catch (NumberFormatException e) {}
                }
            }

            var standardOfLiving = askStandardOfLiving(console);
            var governor = askGovernor(console);
            return new City(id, name, coordinates, LocalDate.now(), area, population, metersAboveSeaLevel, timezone, populationDensity, standardOfLiving, governor);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения! ");
            return null;
        }
    }

    /**
     * Функция запроса координат города
     *
     * @param console консоль
     * @return возвращает координаты города
     * @throws AskBreak исключение для выхода из режима запроса данных
     */
    public static Coordinates askCoordinates (Console console) throws AskBreak {
        try {

            double x;
            while (true) {
                console.println("coordinate x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Double.parseDouble(line);
                        if (x < 330 ) break;
                    } catch (NumberFormatException e) {}
                }
            }

            Long y;
            while (true) {
                console.println("coordinate y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        y = Long.parseLong(line);
                        break;
                    } catch (NumberFormatException e) {}
                }
            }
            return new Coordinates(x, y);

        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения! ");
            return null;
        }
    }

    /**
     * Функция запроса данных о губернаторе
     *
     * @param console консоль
     * @return возвращает данные о губернаторе
     * @throws AskBreak исключение для выхода из режима запроса данных
     */
    public static Human askGovernor(Console console) throws AskBreak {
        try {

            String name;
            while (true) {
                console.println("name of governor: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
            }

            Long age;
            while (true) {
                console.println("age: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        age = Long.parseLong(line);
                        if (age > 0) break;
                    } catch (NumberFormatException e) {
                    }
                }
            }

            Integer height;
            while (true) {
                console.println("height: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        height = Integer.parseInt(line);
                        if (height > 0) break;
                    } catch (NumberFormatException e) {
                    }
                }
            }
            return new Human(name, age, height);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения! ");
            return null;
        }
    }

    /**
     * Функция для запроса стандарта проживания города
     *
     * @param console консоль
     * @return возвращает стандарт проживания города
     * @throws AskBreak исключение для выхода из режима запроса данных
     */
    public static StandardOfLiving askStandardOfLiving(Console console) throws AskBreak {
        try {
            console.println("Standard of living (" + StandardOfLiving.names() + "): ");
            StandardOfLiving standardOfLiving;
            while (true) {
                var line = console.readln().trim().toUpperCase();
                if (line.equalsIgnoreCase("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        standardOfLiving = StandardOfLiving.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException e) {}
                }
                console.println("Standard of living: ");
            }
            return standardOfLiving;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения! ");
            return null;
        }
    }
}