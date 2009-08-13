<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>

<page:applyDecorator name="subcards">
<html>
<head>
	<title><fmt:message key="project"/></title>
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery.validate.min.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/project-cards.js"/>"></script>
</head>

<body>
<div id="tab3">
	<div class="cardForm">
		<form id="editCard" name="editCard" action="<c:url value="/projects/${card.project.id}/cards/${card.id}/" />" method="post">
			<%@include file="formFields.jsp" %>
			<p>
				<input class="buttons" type="submit" value="<fmt:message key="update"/>"/>
				<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="window.location = $('#back').attr('href')"/>
			</p>
		</form>
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