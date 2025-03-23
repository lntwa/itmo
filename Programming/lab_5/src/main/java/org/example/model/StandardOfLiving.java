package org.example.model;

/**
 * Перечисление, представляющее уровень жизни в городе
 *
 * @author Arina Leonteva
 * @version 1.0
 */
public enum StandardOfLiving {
    /**
     * Уровень жизни: очень высокий
     */
    VERY_HIGH,

    /**
     * Уровень жизни: высокий
     */
    HIGH,

    /**
     * Уровень жизни: низкий
     */
    LOW,

    /**
     * Уровень жизни: крайне низкий
     */
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