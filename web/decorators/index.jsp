<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>	
    <%@include file="../WEB-INF/jsp/style-and-js.jsp" %>

    <title>Calopsita - Agile teams project management tool</title>
	<decorator:head />
</head>

<body>

<div id="main">
	<div id="header">
	   <div id="logo">
         <a href="<c:url value="/"/>"><img src="<c:url value="/images/logo-login.png"/>" alt="calopsita" /></a>
       </div>
	</div>
	
	<hr class="separador"/>
	
    <div id="errors">
		<c:if test="${not empty errors}">
			<c:forEach var="error" items="${errors.iterator}">
				<fmt:message key="${error.key}" /><br />
			</c:forEach>
		</c:if>
     </div>
	
	<decorator:body />
</div>

</body>
</html>