<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Selected Sports</title>
</head>
<body>

<c:out value="${'Favourite Sports: '}"/>
<c:if test="${param.cricket != null}" >			
	<c:out value="${'Cricket, '}" />
</c:if>
<c:if test="${param.soccer != null}" >			
	<c:out value="${'Soccer, '}" />
</c:if>
<c:if test="${param.hockey != null}" >			
	<c:out value="${'Hockey, '}" />
</c:if>
<c:if test="${param.tennis != null}" >			
	<c:out value="${'Tennis, '}" />
</c:if>
	<c:out value="${'<br>'}"/>

</body>
</html>