import java.net.*;
import java.io.*;

public class Assignment27bClient {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 5001);

        BufferedReader in = new BufferedReader(
            new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader keyboard = new BufferedReader(
            new InputStreamReader(System.in));

        String msg;
        while (true) {
            System.out.print("Enter message: ");
            msg = keyboard.readLine();
            out.println(msg);

            if (msg.equalsIgnoreCase("exit"))
                break;

            System.out.println("Echo from server: " + in.readLine());
        }

        s.close();
    }
}