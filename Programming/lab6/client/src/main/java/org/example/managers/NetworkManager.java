package org.example.managers;

import org.example.utilities.ExecutionResponse;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class NetworkManager {
    DatagramSocket ds;
    DatagramPacket dp;
    InetAddress host;
    int port;
    int timeout;

    public NetworkManager(int timeout) {
        this.timeout = timeout;
    }
    void defaultInit() throws UnknownHostException, SocketException {
        host = InetAddress.getByName("localhost");
        port = 14775;
        ds = new DatagramSocket();
        ds.setSoTimeout(timeout);
    }
    public boolean init(String[] args) {
        try {
            if (args.length == 0) {
                defaultInit();
            } else {
                var filename = args[0];
                if (filename != null | !filename.isEmpty()) {
                    var fileReader = new Scanner(new File(filename));
                    File file = new File(args[0]);
                    var s = new StringBuilder("");
                    String[] net = new String[2];
                    byte counter = 0;
                    for (int i = 0; i < net.length; i++) {
                        counter += 1;
                        net[i] = fileReader.nextLine();
                    }
                    if (counter != 2) {
                        defaultInit();
                    } else {
                        host = InetAddress.getByName(net[0]);
                        port = Integer.parseInt(net[1]);
                        ds = new DatagramSocket();
                        ds.setSoTimeout(timeout);
                    }
                    System.out.println(s);
                } else {
                    defaultInit();
                }
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean sendData(byte[] arr) { //отправляет массив байтов на сервер через UDP
        try {
            dp = new DatagramPacket(arr, arr.length, host, port);
            ds.send(dp);
            return true;
        } catch (IOException e) {
            return false;
        }


    }

    public static byte[] serializer(Object obj) { //преобразует объект в массив байтов для отправки по сети
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.close();
            byte[] objBytes = bos.toByteArray();
            return objBytes;
        } catch (IOException e) {
            return null;
        }
    }

    public static ExecutionResponse deserialize(byte[] bytes) { //преобразует массив байтов обратно
        if (bytes == null) return new ExecutionResponse(false, "Ответ от сервера не получен, выполнение отменено!");
        InputStream is = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (ExecutionResponse) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] receiveData(int len) { //получает данные от сервера через UDP
        try {
            byte[] arr = new byte[len];
            dp = new DatagramPacket(arr, len);
            ds.receive(dp);
            return arr;
        } catch (IOException e) {
            return null;
        }
    }
}