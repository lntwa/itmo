package org.example.utilities;

/**
 * Класс ответа о выполнении
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class ExecutionResponse {
    /**
     * Код завершения
     */
    private boolean exitCode;

    /**
     * Сообщение
     */
    private String message;

    /**
     * Конструктор
     *
     * @param exitCode код завершения
     * @param message сообщение
     */
    public ExecutionResponse(boolean exitCode, String message) {
        this.exitCode = exitCode;
        this.message = message;
    }
    /**
     * Конструктор
     *
     * @param message сообщение
     */
    public ExecutionResponse(String message) {
        this(true, message);
    }

    /**
     * Функция получения кода завершения
     *
     * @return возвращает код завершения
     */
    public boolean getExitCode() {
        return exitCode;
    }
    /**
     * Функция получения сообщения
     *
     * @return возвращает сообщение
     */
    public String getMessage() {
        return message;
    }
    /**
     * @return возвращает объект, переведенный в строковое представление
     */
    public String toString() {
        return String.valueOf(exitCode) + ";" + message;
    }

}
