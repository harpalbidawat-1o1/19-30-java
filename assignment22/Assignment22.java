
import java.sql.*;
import java.util.Scanner;

public class Assignment22 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/studentdb_22";
        String user = "root";
        String password = "123abccba321";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            System.out.print("Enter username: ");
            String username = sc.nextLine();

            System.out.print("Enter password: ");
            String pass = sc.nextLine();

            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful. Welcome, " + username + "!");
            } else {
                System.out.println("Invalid username or password.");
            }

            rs.close();
            ps.close();
            con.close();
            sc.close();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
