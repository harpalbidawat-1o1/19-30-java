import java.net.*;
import java.io.*;

public class Assignment27bServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(5001);
        System.out.println("Echo server waiting...");

        Socket s = ss.accept();

        BufferedReader in = new BufferedReader(
            new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        String msg;
        while ((msg = in.readLine()) != null) {
            if (msg.equalsIgnoreCase("exit"))
                break;

            System.out.println("Received: " + msg);
            out.println(msg);
        }

        s.close();
        ss.close();
    }
}