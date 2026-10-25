
<%@ page import="java.sql.*" %>
<%
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/jsp_crud", "root", "123abccba321");

String a = request.getParameter("action");

if ("add".equals(a) || "update".equals(a)) {
    String sql = "add".equals(a)
        ? "INSERT INTO patient VALUES(?,?,?,?,?)"
        : "UPDATE patient SET name=?,age=?,disease=?,treatment_status=? WHERE patient_id=?";
    PreparedStatement p = con.prepareStatement(sql);

    if ("add".equals(a))
        p.setInt(1, Integer.parseInt(request.getParameter("id")));
    p.setString("add".equals(a) ? 2 : 1, request.getParameter("name"));
    p.setInt("add".equals(a) ? 3 : 2, Integer.parseInt(request.getParameter("age")));
    p.setString("add".equals(a) ? 4 : 3, request.getParameter("disease"));
    p.setString("add".equals(a) ? 5 : 4, request.getParameter("status"));
    if ("update".equals(a))
        p.setInt(5, Integer.parseInt(request.getParameter("id")));
    p.executeUpdate();
    p.close();
}

if ("delete".equals(a)) {
    PreparedStatement p = con.prepareStatement("DELETE FROM patient WHERE patient_id=?");
    p.setInt(1, Integer.parseInt(request.getParameter("id")));
    p.executeUpdate();
    p.close();
}
%>
<html><body>
<h2>Patient Management</h2>
<form method="post">
Patient ID: <input name="id" type="number"><br>
Name: <input name="name"><br>
Age: <input name="age" type="number"><br>
Disease: <input name="disease"><br>
Treatment Status: <input name="status"><br>
<button name="action" value="add">Add</button>
<button name="action" value="update">Update</button>
<button name="action" value="delete">Delete</button>
</form>
<h3>Patient Records</h3>
<table border="1">
<tr><th>ID</th><th>Name</th><th>Age</th><th>Disease</th><th>Status</th></tr>
<%
Statement s = con.createStatement();
ResultSet r = s.executeQuery("SELECT * FROM patient");
while (r.next()) {
%>
<tr>
<td><%=r.getInt("patient_id")%></td>
<td><%=r.getString("name")%></td>
<td><%=r.getInt("age")%></td>
<td><%=r.getString("disease")%></td>
<td><%=r.getString("treatment_status")%></td>
</tr>
<% }
r.close(); s.close(); con.close();
%>
</table>
</body></html>
