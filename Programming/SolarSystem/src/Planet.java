public class Planet {
    String name; //поля класса
    Double radius;
    Double sunDistance;

    public String getName() { //метод класа
        return name;
    }

    public void setName(String name) { //это метод класса, который использует this
        this.name = name;
    }

    public double toThousandKm(String param) {
        double result = 0;
        switch (param) {
            case "sunDistance":
                result = this.sunDistance / 1000;
            case "radius":
                result = this.radius / 1000;
        }
        return result;
    }
}
