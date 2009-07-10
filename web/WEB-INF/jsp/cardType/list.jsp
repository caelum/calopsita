<%@include file="../javascripts.jspf" %>
<h1><fmt:message key="cardTypes"/></h1>
<ul>
<c:forEach items="${cardTypeList}" var="type">
	<li>${type }</li>
</c:forEach>
</ul>