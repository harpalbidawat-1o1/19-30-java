
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class Assignment24 extends JFrame {
    JTextField id = new JTextField(), title = new JTextField(),
               author = new JTextField(), status = new JTextField("Available");
    DefaultTableModel model = new DefaultTableModel(
        new String[]{"ID", "Title", "Author", "Status"}, 0);
    JTable table = new JTable(model);

    Connection con() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/librarydb_24", "root", "123abccba321");
    }

    Assignment24() {
        setTitle("Library Management");
        setSize(650, 400);
        setLayout(new BorderLayout());

        JPanel p = new JPanel(new GridLayout(4, 2));
        p.add(new JLabel("Book ID")); p.add(id);
        p.add(new JLabel("Title")); p.add(title);
        p.add(new JLabel("Author")); p.add(author);
        p.add(new JLabel("Status")); p.add(status);

        JPanel b = new JPanel();
        String[] actions = {"Add", "Update", "Delete", "View"};
        for (String a : actions) {
            JButton btn = new JButton(a);
            b.add(btn);
            btn.addActionListener(e -> run(a));
        }

        add(p, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(b, BorderLayout.SOUTH);
        load();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void run(String action) {
        String sql = switch (action) {
            case "Add" -> "INSERT INTO books VALUES(?,?,?,?)";
            case "Update" -> "UPDATE books SET title=?,author=?,status=? WHERE book_id=?";
            case "Delete" -> "DELETE FROM books WHERE book_id=?";
            default -> "";
        };
        if (action.equals("View")) { load(); return; }

        try (Connection c = con(); PreparedStatement s = c.prepareStatement(sql)) {
            if (action.equals("Add")) {
                s.setInt(1, Integer.parseInt(id.getText()));
                s.setString(2, title.getText());
                s.setString(3, author.getText());
                s.setString(4, status.getText());
            } else if (action.equals("Update")) {
                s.setString(1, title.getText());
                s.setString(2, author.getText());
                s.setString(3, status.getText());
                s.setInt(4, Integer.parseInt(id.getText()));
            } else {
                s.setInt(1, Integer.parseInt(id.getText()));
            }
            s.executeUpdate();
            load();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void load() {
        model.setRowCount(0);
        try (Connection c = con();
             ResultSet r = c.createStatement().executeQuery("SELECT * FROM books")) {
            while (r.next())
                model.addRow(new Object[]{r.getInt(1), r.getString(2),
                    r.getString(3), r.getString(4)});
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Assignment24());
    }
}
