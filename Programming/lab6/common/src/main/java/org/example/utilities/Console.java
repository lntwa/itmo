package org.example.utilities;

import java.util.Scanner;

/**
 * Класс для работы с консольным вводом и выводом
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public class Console {

    private Scanner scanner;
    private String prompt;

    /**
     * Создает новый объект Console и приглашением "> "
     */
    public Console() {
        this.scanner = new Scanner(System.in);
        this.prompt = "> ";
    }

    /**
     * Выводит объект в консоль с переводом строки
     *
     * @param obj объект для вывода
     */
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Считывает строку из консоли
     *
     * @return введенная строка
     */
    public String readln() {
        return scanner.nextLine();
    }

    /**
     * Проверяет, доступен ли ввод из консоли
     *
     * @return true, если ввод доступен, иначе false
     */
    public boolean isCanReadln() {
        return scanner.hasNextLine();
    }

    /**
     * Выводит сообщение об ошибке в консоль
     *
     * @param obj объект с сообщением об ошибке
     */
    public void printError(Object obj) {
        System.err.println("Ошибка: " + obj);
    }

    /**
     * Выводит приглашение для ввода
     */
    public void prompt() {
        System.out.print(prompt);
    }

    /**
     * Возвращает текущее приглашение для ввода
     *
     * @return строка с приглашением
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Устанавливает сканер для чтения из файла
     *
     * @param obj сканер для файла
     */
    public void selectFileScanner(Scanner obj) {
        this.scanner = obj;
    }

    /**
     * Устанавливает сканер для чтения из консоли
     */
    public void selectConsoleScanner() {
        this.scanner = new Scanner(System.in);
    }
}