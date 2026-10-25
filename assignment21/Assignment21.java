
import java.sql.*;

public class Assignment21 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/studentdb_21";
        String user = "root";
        String password = "123abccba321";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            System.out.println("Student database connected successfully.");

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT DATABASE()");

            if (rs.next()) {
                System.out.println("Database Name: " + rs.getString(1));
            }

            rs.close();
            stmt.close();
            con.close();

            System.out.println("Connection closed successfully.");
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            System.out.println(e.getMessage());
        }
    }
}
