<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>
<page:applyDecorator name="subcards">
<html>
<head>
	<title><fmt:message key="cards"/></title>
</head>

<body>
<div id="tab3">
	<div id="cards">
		<c:if test="${not empty cards}">
			<calopsita:cards cards="${cards}" listId="cards" description="true" />
		</c:if>
		<c:if test="${empty cards}">
			<fmt:message key="subcards.empty"/>
		</c:if>
	</div>
	<div id="card" class="information">
		<h4>Card</h4>
	    <p><fmt:message key="project.name"/>: ${card.name}</p>
	    <p><fmt:message key="project.description"/>: ${card.description}</p>
	</div>
</div>
</body>
</html>
</page:applyDecorator>