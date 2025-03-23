public class Car {

    String model; //поля класса
    int maxSpeed;

    public Car(String model, int maxSpeed) { // конструктор класса с аргументами
        this.model = model;
        this.maxSpeed = maxSpeed;
    }

    public static void main(String[] args) {
        Car bugatti = new Car("Bugatti Veyron", 407); //создание объекта, при этом вызывается конструктор с аргументами
        System.out.println(bugatti.model); //вывод данных
        System.out.println(bugatti.maxSpeed);
    }

}