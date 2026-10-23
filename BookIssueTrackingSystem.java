
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BookIssueTrackingSystem extends JFrame {
    JTextField idField, bookField, studentField, issueDateField, returnDateField;
    JTable table;
    DefaultTableModel model;

    String url = "jdbc:mysql://localhost:3306/library_db";
    String user = "root";
    String password = "YOUR_PASSWORD";

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    BookIssueTrackingSystem() {
        setTitle("Book Issue Tracking System");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        idField = new JTextField();
        bookField = new JTextField();
        studentField = new JTextField();
        issueDateField = new JTextField();
        returnDateField = new JTextField();

        form.add(new JLabel("Issue ID (for update/delete):"));
        form.add(idField);
        form.add(new JLabel("Book ID:"));
        form.add(bookField);
        form.add(new JLabel("Student Name:"));
        form.add(studentField);
        form.add(new JLabel("Issue Date (YYYY-MM-DD):"));
        form.add(issueDateField);
        form.add(new JLabel("Return Date (YYYY-MM-DD):"));
        form.add(returnDateField);

        JPanel buttons = new JPanel();
        JButton addButton = new JButton("Add");
        JButton viewButton = new JButton("View");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        buttons.add(addButton);
        buttons.add(viewButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);

        model = new DefaultTableModel(
            new String[]{"Issue ID", "Book ID", "Student Name", "Issue Date", "Return Date"}, 0
        );
        table = new JTable(model);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addRecord());
        viewButton.addActionListener(e -> viewRecords());
        updateButton.addActionListener(e -> updateRecord());
        deleteButton.addActionListener(e -> deleteRecord());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int r = table.getSelectedRow();
                idField.setText(model.getValueAt(r, 0).toString());
                bookField.setText(model.getValueAt(r, 1).toString());
                studentField.setText(model.getValueAt(r, 2).toString());
                issueDateField.setText(model.getValueAt(r, 3).toString());
                Object date = model.getValueAt(r, 4);
                returnDateField.setText(date == null ? "" : date.toString());
            }
        });

        viewRecords();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void addRecord() {
        String sql = "INSERT INTO book_issues (book_id, student_name, issue_date, return_date) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(bookField.getText()));
            ps.setString(2, studentField.getText());
            ps.setDate(3, Date.valueOf(issueDateField.getText()));

            if (returnDateField.getText().trim().isEmpty()) {
                ps.setNull(4, Types.DATE);
            } else {
                ps.setDate(4, Date.valueOf(returnDateField.getText()));
            }

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record added");
            viewRecords();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void viewRecords() {
        model.setRowCount(0);
        String sql = "SELECT * FROM book_issues";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("issue_id"),
                    rs.getInt("book_id"),
                    rs.getString("student_name"),
                    rs.getDate("issue_date"),
                    rs.getDate("return_date")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void updateRecord() {
        String sql = "UPDATE book_issues SET book_id=?, student_name=?, issue_date=?, return_date=? WHERE issue_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(bookField.getText()));
            ps.setString(2, studentField.getText());
            ps.setDate(3, Date.valueOf(issueDateField.getText()));

            if (returnDateField.getText().trim().isEmpty()) {
                ps.setNull(4, Types.DATE);
            } else {
                ps.setDate(4, Date.valueOf(returnDateField.getText()));
            }

            ps.setInt(5, Integer.parseInt(idField.getText()));

            int rows = ps.executeUpdate();
            JOptionPane.showMessageDialog(this,
                rows > 0 ? "Record updated" : "Issue ID not found");
            viewRecords();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    void deleteRecord() {
        String sql = "DELETE FROM book_issues WHERE issue_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(idField.getText()));

            int rows = ps.executeUpdate();
            JOptionPane.showMessageDialog(this,
                rows > 0 ? "Record deleted" : "Issue ID not found");
            viewRecords();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookIssueTrackingSystem());
    }
}
