
import java.sql.*;
import java.util.Scanner;

public class Assignment20 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/studentdb_java";
        String user = "root";
        String password = "123abccba321";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            while (true) {
                System.out.println("\n1. Insert Student");
                System.out.println("2. Update Student");
                System.out.println("3. Delete Student");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                if (choice == 1) {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Course: ");
                    String course = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();

                    PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO students VALUES (?, ?, ?, ?)"
                    );
                    ps.setInt(1, id);
                    ps.setString(2, name);
                    ps.setString(3, course);
                    ps.setDouble(4, marks);

                    System.out.println(ps.executeUpdate() > 0
                        ? "Student inserted successfully."
                        : "Insert failed.");
                    ps.close();

                } else if (choice == 2) {
                    System.out.print("Enter ID to update: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter new Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter new Course: ");
                    String course = sc.nextLine();

                    System.out.print("Enter new Marks: ");
                    double marks = sc.nextDouble();

                    PreparedStatement ps = con.prepareStatement(
                        "UPDATE students SET name=?, course=?, marks=? WHERE id=?"
                    );
                    ps.setString(1, name);
                    ps.setString(2, course);
                    ps.setDouble(3, marks);
                    ps.setInt(4, id);

                    System.out.println(ps.executeUpdate() > 0
                        ? "Student updated successfully."
                        : "Student ID not found.");
                    ps.close();

                } else if (choice == 3) {
                    System.out.print("Enter ID to delete: ");
                    int id = sc.nextInt();

                    PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM students WHERE id=?"
                    );
                    ps.setInt(1, id);

                    System.out.println(ps.executeUpdate() > 0
                        ? "Student deleted successfully."
                        : "Student ID not found.");
                    ps.close();

                } else if (choice == 4) {
                    con.close();
                    sc.close();
                    System.out.println("Program terminated.");
                    break;

                } else {
                    System.out.println("Invalid choice.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
