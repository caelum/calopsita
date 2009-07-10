<%@include file="../javascripts.jspf" %>
<h2><fmt:message key="cardTypes"/></h2>
<ul>
<c:forEach items="${cardTypeList}" var="type">
	<li>${type }</li>
</c:forEach>
</ul>