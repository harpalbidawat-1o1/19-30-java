
<%@ page import="java.sql.*" %>
<%
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/jsp_crud", "root", "123abccba321");

String a = request.getParameter("action");

if ("add".equals(a) || "update".equals(a)) {
    String sql = "add".equals(a)
        ? "INSERT INTO employee(name,department,salary) VALUES(?,?,?)"
        : "UPDATE employee SET name=?,department=?,salary=? WHERE id=?";
    PreparedStatement p = con.prepareStatement(sql);
    p.setString(1, request.getParameter("name"));
    p.setString(2, request.getParameter("department"));
    p.setDouble(3, Double.parseDouble(request.getParameter("salary")));
    if ("update".equals(a))
        p.setInt(4, Integer.parseInt(request.getParameter("id")));
    p.executeUpdate();
    p.close();
}

if ("delete".equals(a)) {
    PreparedStatement p = con.prepareStatement("DELETE FROM employee WHERE id=?");
    p.setInt(1, Integer.parseInt(request.getParameter("id")));
    p.executeUpdate();
    p.close();
}
%>
<html><body>
<h2>Employee Management</h2>
<form method="post">
ID (for update/delete): <input name="id" type="number"><br>
Name: <input name="name"><br>
Department: <input name="department"><br>
Salary: <input name="salary" type="number" step="0.01"><br>
<button name="action" value="add">Add</button>
<button name="action" value="update">Update</button>
<button name="action" value="delete">Delete</button>
</form>
<h3>Employee Records</h3>
<table border="1">
<tr><th>ID</th><th>Name</th><th>Department</th><th>Salary</th></tr>
<%
Statement s = con.createStatement();
ResultSet r = s.executeQuery("SELECT * FROM employee");
while (r.next()) {
%>
<tr>
<td><%=r.getInt("id")%></td>
<td><%=r.getString("name")%></td>
<td><%=r.getString("department")%></td>
<td><%=r.getDouble("salary")%></td>
</tr>
<% }
r.close(); s.close(); con.close();
%>
</table>
</body></html>
