public class Main {
    public static void main (String[] args) {
        Planet earth = new Planet();
        earth.radius = 6371.0;       // Радиус Земли в километрах
        earth.sunDistance = 149597870.7; // Расстояние от Земли до Солнца в километрах

        double distanceToSunInThousands = earth.toThousandKm("sunDistance"); // Вызываем метод с параметром "sunDistance"
        System.out.println(distanceToSunInThousands); // Выводит: 149597.8707

        double radiusInThousands = earth.toThousandKm("radius"); // Вызываем метод с параметром "radius"
        System.out.println(radiusInThousands); // Выводит: 6.371

    }
}