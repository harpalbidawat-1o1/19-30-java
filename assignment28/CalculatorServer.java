import java.rmi.*;
import java.rmi.registry.*;

public class CalculatorServer {
    public static void main(String[] args) throws Exception {
        Calculator c = new CalculatorImpl();
        LocateRegistry.createRegistry(1099);
        Naming.rebind("Calculator", c);
        System.out.println("Calculator server ready");
    }
}