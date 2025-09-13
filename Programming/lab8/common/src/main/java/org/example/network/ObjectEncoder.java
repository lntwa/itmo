package org.example.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * Утилитный класс для кодирования объектов для UDP-передачи.
 */
public final class ObjectEncoder {
    private ObjectEncoder() {
    }

    /**
     * Кодирует объект в ByteBuffer для UDP-передачи.
     *
     * @param object объект для кодирования.
     * @return ByteBuffer, содержащий закодированный объект.
     * @throws IOException если возникла ошибка при кодировании объекта.
     */
    public static ByteBuffer encodeObject(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.flush();

        // Для UDP не обязательно включать размер в начало сообщения,
        // так как DatagramPacket уже содержит информацию о длине
        return ByteBuffer.wrap(baos.toByteArray());
    }

    /**
     * Альтернативный метод кодирования без размера в начале (оптимизировано для UDP).
     *
     * @param object объект для кодирования.
     * @return массив байтов с закодированным объектом.
     * @throws IOException если возникла ошибка при кодировании объекта.
     */
    public static byte[] encodeObjectToBytes(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.flush();
        return baos.toByteArray();
    }
}