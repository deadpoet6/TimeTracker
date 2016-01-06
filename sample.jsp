<%-- use the 'taglib' directive to make the JSTL 1.0 core tags available; use the uri 
"http://java.sun.com/jsp/jstl/core" for JSTL 1.1 --%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%-- use the 'jsp:useBean' standard action to create the Date object;  the object is set 
as an attribute in page scope 
--%>
<jsp:useBean id="date" class="java.util.Date" />

<html>
<head><title>First JSP</title></head>
<body>
<h2>Here is today's date</h2>

<c:out value="${date}" />

</body>
</html>
<changes has been made>