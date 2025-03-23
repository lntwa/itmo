package org.example.commands;

import org.example.interfaces.Executable;
import org.example.utilities.ExecutionResponse;

import java.util.Objects;

/**
 * Абстрактный класс команды, представляющий команду с именем и описанием
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public abstract class Command implements Executable {
    /**
     * Название команды
     */
    private final String name;

    /**
     * Описание команды
     */
    private final String description;

    /**
     * Конструктор
     *
     * @param name название команды
     * @param description описание команды
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }
    /**
     * Возвращает название команды
     *
     * @return название команды
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает описание команды
     *
     * @return описание команды
     */
    public String getDescription() {
        return description;
    }

    /**
     * Сравнивает текущую команду с другим объектом
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && description.equals(command.description);
    }

    /**
     * Возвращает хэш-код команды на основе её имени и описания
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    /**
     * Возвращает строковое представление команды
     */
    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
