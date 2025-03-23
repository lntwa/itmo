public class Main {
        public class A {
        int x; static int statX = 5;
        public A(int x) {
            this.x = statX++; statX++;
        }
        public static void main(String[] args) {
            var a1 = new A(2);
            var a2 = new A(1);
            System.out.println(a1.x + ";" + a2.x);
        }
    }
}