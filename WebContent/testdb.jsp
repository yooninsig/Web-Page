<%@page contentType="text/html; charset=euc-kr" errorPage="DBError.jsp" %>
<%@page import="java.sql.*"%>
<%
    String name = "name";
    String id = "ID";
    String password = "asd";
    if (name == null || id == null || password == null) 
        throw new Exception("�����͸� �Է��Ͻʽÿ�.");
    Connection conn = null;
    Statement stmt = null;
    try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/webdb?serverTimezone=UTC", "root", "student");
        if (conn == null)
            throw new Exception("�����ͺ��̽��� ������ �� �����ϴ�.");
        stmt = conn.createStatement();
        String command = String.format("insert into userinfo " +
                  "(name, id, password) values ('%s', '%s', '%s');",
                  name, id, password);
        int rowNum = stmt.executeUpdate(command);
        if (rowNum < 1)
            throw new Exception("�����͸� DB�� �Է��� �� �����ϴ�.");
    }
    finally {
        try { 
            stmt.close();
        } 
        catch (Exception ignored) {
        }
        try { 
            conn.close();
        } 
        catch (Exception ignored) {
        }
    }
    
%>
