
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BookIssueTrackingSystem extends JFrame {
    JTextField id = new JTextField();
    JTextField book = new JTextField();
    JTextField student = new JTextField();
    JTextField issue = new JTextField();
    JTextField ret = new JTextField();
    JTextArea area = new JTextArea();

    Connection con() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/library_db", "root", "password");
    }

    BookIssueTrackingSystem() {
        setLayout(new GridLayout(0, 2, 5, 5));

        add(new JLabel("Issue ID")); add(id);
        add(new JLabel("Book ID")); add(book);
        add(new JLabel("Student Name")); add(student);
        add(new JLabel("Issue Date")); add(issue);
        add(new JLabel("Return Date")); add(ret);

        JButton a = new JButton("Add");
        JButton v = new JButton("View");
        JButton u = new JButton("Update");
        JButton d = new JButton("Delete");

        add(a); add(v); add(u); add(d);
        add(new JScrollPane(area));

        a.addActionListener(e -> run("INSERT INTO book_issues VALUES(?,?,?,?,?)"));
        u.addActionListener(e -> run("UPDATE book_issues SET book_id=?,student_name=?,issue_date=?,return_date=? WHERE issue_id=?"));
        d.addActionListener(e -> run("DELETE FROM book_issues WHERE issue_id=?"));
        v.addActionListener(e -> view());

        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void run(String sql) {
        try (Connection c = con(); PreparedStatement p = c.prepareStatement(sql)) {
            if (sql.startsWith("INSERT")) {
                p.setInt(1, Integer.parseInt(id.getText()));
                p.setInt(2, Integer.parseInt(book.getText()));
                p.setString(3, student.getText());
                p.setDate(4, Date.valueOf(issue.getText()));
                p.setDate(5, Date.valueOf(ret.getText()));
            } else if (sql.startsWith("UPDATE")) {
                p.setInt(1, Integer.parseInt(book.getText()));
                p.setString(2, student.getText());
                p.setDate(3, Date.valueOf(issue.getText()));
                p.setDate(4, Date.valueOf(ret.getText()));
                p.setInt(5, Integer.parseInt(id.getText()));
            } else {
                p.setInt(1, Integer.parseInt(id.getText()));
            }
            p.executeUpdate();
            JOptionPane.showMessageDialog(this, "Operation successful");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void view() {
        area.setText("");
        try (Connection c = con();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT * FROM book_issues")) {
            while (r.next())
                area.append(r.getInt(1) + " | " + r.getInt(2) + " | " +
                    r.getString(3) + " | " + r.getDate(4) + " | " +
                    r.getDate(5) + "\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new BookIssueTrackingSystem();
    }
}

