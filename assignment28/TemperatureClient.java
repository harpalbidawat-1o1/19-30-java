import java.rmi.*;
import java.util.*;

public class TemperatureClient {
    public static void main(String[] args) throws Exception {
        Temperature t = (Temperature) Naming.lookup("rmi://localhost/Temperature");
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Celsius: ");
        double c = sc.nextDouble();
        System.out.println("Fahrenheit: " + t.celsiusToFahrenheit(c));

        System.out.print("Enter Fahrenheit: ");
        double f = sc.nextDouble();
        System.out.println("Celsius: " + t.fahrenheitToCelsius(f));

        sc.close();
    }
}