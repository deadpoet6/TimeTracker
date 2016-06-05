<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Time tracker</title>
</head>
<body>

<%
try {
	
	
	String dbUrl = "jdbc:mysql://localhost/mydatabase";
    String dbClass = "com.mysql.jdbc.Driver";
    String query = "Select distinct(table_name) from INFORMATION_SCHEMA.TABLES";
    String username = "root";
    String password = "";
    
    //String connectionURL = "jdbc:mysql://host/db";
    //Connection connection = null; 
    //Class.forName("com.mysql.jdbc.Driver").newInstance(); 
    //connection = DriverManager.getConnection(connectionURL, "username", "password");
    //if(!connection.isClosed())
         //out.println("Successfully connected to " + "MySQL server using TCP/IP...");
    //connection.close();
}catch(Exception ex){
    out.println("Unable to connect to database"+ex);
} 

%>
   <h1>Ikram is working on this page </h1>
</body>
</html>