package org.example.managers;

import com.opencsv.CSVReader;
import org.example.model.City;
import org.example.utilities.Console;

import java.io.*;
import java.util.*;

/**
 * Класс для работы с сохранением и загрузкой коллекции в формате CSV
 * Обеспечивает сериализацию коллекции в строку CSV и десериализацию строки CSV в коллекцию
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class DumpManager {
    /**
     * Имя файла, в который сохраняется или из которого загружается коллекция
     */
    private final String fileName;

    /**
     * Консоль
     */
    private final Console console;

    /**
     * Конструктор
     *
     * @param fileName имя файла для сохранения/загрузки коллекции
     * @param console  консоль
     */
    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }
    /**
     * Преобразует коллекцию городов в строку в формате CSV
     *
     * @param collection коллекция городов
     * @return строка в формате CSV или null, если произошла ошибка
     */
    private String collectionToCSV(Collection<City> collection) {
        try (StringWriter sw = new StringWriter(); // класс для записи в строку
             BufferedWriter writer = new BufferedWriter(sw)) { // класс для буферизации данных

            for (City city : collection) {
                String[] fields = City.toArray(city);
                String line = String.join(",", fields); // объединение полей с разделителем ","
                writer.write(line);
                writer.newLine();
            }

            writer.flush(); // накопил данные и смыл :) (сброс буфера)
            return sw.toString();
        } catch (Exception e) {
            console.printError("Ошибка сериализации: " + e.getMessage());
            return null;
        }
    }

    /**
     * Сохраняет коллекцию городов в файл в формате CSV
     *
     * @param collection коллекция городов
     */
    public void writeToCollection(Collection<City> collection) {
        String csv = collectionToCSV(collection);
        if (csv == null) {
            console.printError("Ошибка сериализации коллекции");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(fileName); // записывает текстовые данные в файл
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            bufferedWriter.write(csv);
            bufferedWriter.flush(); // накопил данные и смыл :)

        } catch (IOException e) {
            console.printError("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    /**
     * Преобразует строку в формате CSV в коллекцию городов
     *
     * @param s строка в формате CSV
     * @return коллекция городов или null, если произошла ошибка
     */
    private ArrayDeque<City> CSV2collection(String s) {
        try {
            StringReader sr = new StringReader(s);
            CSVReader csvReader = new CSVReader(sr);
            ArrayDeque<City> ds = new ArrayDeque<>();
            String[] record;

            while ((record = csvReader.readNext()) != null) {
                console.println("Считывание строки: " + Arrays.toString(record));
                try {
                    City d = City.fromArray(record);
                    if (d != null && d.validate()) {
                        ds.add(d);
                    } else {
                        console.printError("Файл с коллекцией содержит недействительные данные: " + Arrays.toString(record));
                    }
                } catch (Exception e) {
                    console.printError("Ошибка при обработке строки: " + Arrays.toString(record));
                }
            }
            csvReader.close();
            return ds;
        } catch (Exception e) {
            console.printError("Ошибка десериализации: " + e.getMessage());
            return null;
        }
    }

    /**
     * Загружает коллекцию городов из файла в формате CSV
     *
     * @param collection коллекция, в которую будут загружены данные
     */
    public void readCollection(Collection<City> collection) {
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new Scanner(new File(fileName))) {
                var s = new StringBuilder("");
                while (fileReader.hasNextLine()) {
                    s.append(fileReader.nextLine());
                    s.append("\n");
                }
                collection.clear();
                for (var e : CSV2collection(s.toString()))
                    collection.add(e);
                if (collection != null) {
                    console.println("Коллекция успешна загружена!");
                } else
                    console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }
    }
}