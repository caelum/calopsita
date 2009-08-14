<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<page:applyDecorator name="cards">
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

	<div id="form" class="cardForm">
		<form id="cardForm" name="addCard" action="<c:url value="/projects/${project.id }/cards/"/>" method="post">
			<%@include file="formFields.jsp" %>
			<p>
				<input class="buttons" type="submit" value="<fmt:message key="add"/>" />
				<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="toggle('cardForm');" />
			</p>
		</form>
	</div>
		
	<div class="information">
		<h4><fmt:message key="recently.added.cards"/></h4>
		<ul id="recent-cards" class="stories">
			<c:forEach items="${project.lastAddedCards}" var="card" varStatus="s">
				<%@include file="storyLine.jsp" %>
			</c:forEach>
		</ul>
	</div>
</div>
</body>
</html>
</page:applyDecorator>