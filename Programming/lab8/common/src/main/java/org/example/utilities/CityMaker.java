package org.example.utilities;

import java.time.LocalDate;
import org.example.exceptions.InvalidFieldException;
import org.example.models.City;
import org.example.network.BasicUserIO;
import org.example.models.Coordinates;
import org.example.models.Human;
import org.example.models.StandardOfLiving;

import static org.example.utilities.CoordinatesMaker.parseCoordinates;

/**
 * Класс для создания объектов City
 */
public final class CityMaker {
    private CityMaker() {}

    public static City parseCity(BasicUserIO io, int id) throws InvalidFieldException {
        return new City(
                id,
                BasicParsers.Repeater.doGet(CityMaker::parseName, io),
                BasicParsers.Repeater.doGet(
                        io_ -> parseCoordinates(
                                io_,
                                "Coordinates",
                                City.VALIDATOR::validateCoordinates
                        ),
                        io
                ),
                LocalDate.now(),
                BasicParsers.Repeater.doGet(CityMaker::parseArea, io),
                BasicParsers.Repeater.doGet(CityMaker::parsePopulation, io),
                BasicParsers.Repeater.doGet(CityMaker::parseMetersAboveSeaLevel, io),
                BasicParsers.Repeater.doGet(CityMaker::parseTimezone, io),
                BasicParsers.Repeater.doGet(CityMaker::parsePopulationDensity, io),
                StandardOfLiving.valueOf(BasicParsers.Repeater.doGet(CityMaker::parseStandardOfLiving, io)),
                BasicParsers.Repeater.doGet(CityMaker::parseGovernor, io)
        );
    }

    public static String parseStandardOfLiving(BasicUserIO io) throws InvalidFieldException {
        String res = BasicParsers.parseString(io, "Standard of living: ");
        City.VALIDATOR.validateStandardOfLiving(StandardOfLiving.valueOf(res));
        return res;
    }

    // Аналогичные методы для полей City
    public static String parseName(BasicUserIO io) throws InvalidFieldException {
        String res = BasicParsers.parseString(io, "Name: ");
        City.VALIDATOR.validateName(res);
        return res;
    }

    public static Double parseArea(BasicUserIO io) throws InvalidFieldException {
        Double res = BasicParsers.parseDouble(io, "Area: ", "Площадь должна быть больше нуля.");
        City.VALIDATOR.validateArea(res);
        return res;
    }

    public static Coordinates parseCoordinates(BasicUserIO io, String prompt, AbstractValidator<Coordinates> validator) throws InvalidFieldException {
        try {
            io.writeln(prompt);
            Coordinates coordinates = CoordinatesMaker.parseCoordinates(io);
            validator.validate(coordinates);
            return coordinates;
        } catch (InvalidFieldException e) {
            throw new InvalidFieldException("Не удалось создать координаты.", e);
        }
    }

    public static Human parseGovernor(BasicUserIO io) throws InvalidFieldException {
        try {
            io.writeln("Governor info:");
            String name = BasicParsers.parseString(io, "Name: ");
            Long age = BasicParsers.parseLong(io, "Age: ", "Возраст должен быть положительным");
            Integer height = BasicParsers.parseInt(io, "Height: ", "Рост должен быть положительным");
            return new Human(name, age, height);
        } catch (InvalidFieldException e) {
            throw new InvalidFieldException("Не удалось создать губернатора", e);
        }
    }

    public static Integer parsePopulation(BasicUserIO io) throws InvalidFieldException {
        Integer res = BasicParsers.parseInt(io, "Population: ", "Население должно быть больше 0!");
        City.VALIDATOR.validatePopulation(res);
        return res;
    }

    public static Long parseMetersAboveSeaLevel(BasicUserIO io) throws InvalidFieldException {
        Long res = BasicParsers.parseLong(io, "Meters above sea level: ", "Расстояние над морем должно быть больше 0!");
        City.VALIDATOR.validateMetersAboveSeaLevel(res);
        return res;
    }

    public static Double parseTimezone(BasicUserIO io) throws InvalidFieldException {
        Double res = BasicParsers.parseDouble(io, "Timezone: ", "Временная зона должна быть больше -13 и не превышать 15");
        City.VALIDATOR.validateTimezone(res);
        return res;
    }

    public static Float parsePopulationDensity(BasicUserIO io) throws InvalidFieldException {
        Float res = BasicParsers.parseFloat(io, "Population density: ", "Плотность населения должна быть больше 0!");
        City.VALIDATOR.validatePopulationDensity(res);
        return res;
    }

    
}