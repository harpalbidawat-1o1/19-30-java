import java.rmi.*;

public class CalculatorClient {
    public static void main(String[] args) throws Exception {
        Calculator c = (Calculator) Naming.lookup("rmi://localhost/Calculator");

        System.out.println("Addition: " + c.add(10, 5));
        System.out.println("Subtraction: " + c.subtract(10, 5));
        System.out.println("Multiplication: " + c.multiply(10, 5));
        System.out.println("Division: " + c.divide(10, 5));
    }
}