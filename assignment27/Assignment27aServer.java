import java.net.*;
import java.io.*;

public class Assignment27aServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(5000);
        System.out.println("Server waiting for client...");

        Socket s = ss.accept();
        System.out.println("Client connected");

        BufferedReader in = new BufferedReader(
            new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader keyboard = new BufferedReader(
            new InputStreamReader(System.in));

        String msg;
        while (true) {
            msg = in.readLine();
            if (msg == null || msg.equalsIgnoreCase("exit"))
                break;

            System.out.println("Client: " + msg);
            System.out.print("Server: ");
            out.println(keyboard.readLine());
        }

        s.close();
        ss.close();
    }
}