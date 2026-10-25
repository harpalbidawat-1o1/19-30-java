
import java.sql.*;

public class Assignment23 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/studentdb_23";
        String user = "root";
        String password = "123abccba321";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            Statement stmt = con.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );

            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");

            System.out.println("Employee Records:");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " " +
                    rs.getString("name") + " " +
                    rs.getString("department") + " " +
                    rs.getDouble("salary")
                );
            }

            System.out.println("\nFirst Employee:");
            if (rs.first()) {
                System.out.println(rs.getString("name"));
            }

            System.out.println("\nLast Employee:");
            if (rs.last()) {
                System.out.println(rs.getString("name"));
            }

            System.out.println("\nPrevious Employee:");
            if (rs.previous()) {
                System.out.println(rs.getString("name"));
            }

            System.out.println("\nSecond Employee:");
            if (rs.absolute(2)) {
                System.out.println(rs.getString("name"));
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
