package org.example.model;

/**
 * Перечисление, представляющее уровень жизни в городе
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public enum StandardOfLiving {
    VERY_HIGH,
    HIGH,
    LOW,
    ULTRA_LOW;

    /**
     * Возвращает строку, содержащую все значения перечисления, разделенные запятой
     *
     * @return строка со всеми значениями
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var livingType : values()) {
            nameList.append(livingType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}