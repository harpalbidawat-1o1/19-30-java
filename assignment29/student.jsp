<html>
<body>
    <h2>Student Information</h2>
    <%
        String name = request.getParameter("name");
        String roll = request.getParameter("roll");
        String course = request.getParameter("course");
        String marks = request.getParameter("marks");
    %>
    Name: <%= name %><br><br>
    Roll Number: <%= roll %><br><br>
    Course: <%= course %><br><br>
    Marks: <%= marks %><br><br>
</body>
</html>