import java.rmi.*;
import java.rmi.registry.*;

public class TemperatureServer {
    public static void main(String[] args) throws Exception {
        Temperature t = new TemperatureImpl();
        LocateRegistry.createRegistry(1099);
        Naming.rebind("Temperature", t);
        System.out.println("Temperature server ready");
    }
}