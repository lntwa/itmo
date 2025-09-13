package org.example.managers;

import org.example.commands.Container;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

public class NetworkManager {
    DatagramChannel dc;
    int port;
    SocketAddress addr;
    int timeout;
    byte[] bytes = new byte[5096];


    public NetworkManager(int port, int timeout) {
        this.port = port;
    }
    public static final Logger logger = Logger.getLogger(NetworkManager.class.getName());
    public boolean init() {
        try {
            addr = new InetSocketAddress(port); //создаем адрес сокета
            dc = DatagramChannel.open(); //открываем юдп канал
            dc.bind(addr); //привязываем канал к адресу
            dc.configureBlocking(false);
            return true;
        } catch (SocketException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendData(byte data[]) {
        try {
            ByteBuffer buf = ByteBuffer.wrap(data);
            dc.send(buf, addr);
            return true;
        } catch (IOException e) {
            return false;

        }

    }

    public byte[] receiveData(int len)  {
        try {
            ByteBuffer buf = ByteBuffer.allocate(len);
            addr = dc.receive(buf);
            if (addr != null) {
                logger.info("Получен запрос от клиента!");
                return buf.array();}
            return null;
        } catch (IOException e) {
            logger.info("Не удалось получить данные.");
            return null;
        }
    }
    public static byte[] serializer(Object obj)  {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.close();
            byte[] objBytes = bos.toByteArray();
            logger.info("Ответ успешно сериализован!");
            return objBytes;

        }
        catch (IOException e) {return null;}
    }
    public static Container deserialize(byte[] bytes) {
        if (bytes == null) return null;
        InputStream is = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            logger.info("Команда успешно десериализована!");
            return (Container) ois.readObject();
        } catch (IOException e) {
            logger.info("Не удалось десереализовать объект");
            return null;
        } catch (ClassNotFoundException e) {
            logger.info("Не удалось десереализовать объект");
            return null;
        }
    }

}