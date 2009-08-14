<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>

<page:applyDecorator name="subcards">
<html>
<head>
	<title><fmt:message key="project"/></title>
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery.validate.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/project-cards.js"/>"></script>
</head>

<body>
<div id="tab3">
	<div id="form" class="cardForm">
		<form id="cardForm" name="addCard" action="<c:url value="/projects/${card.project.id }/cards/${parent.id }/subcards/"/>" method="post">
			<%@include file="formFields.jsp" %>
		    <p>
		    	<input class="buttons" type="submit" value="<fmt:message key="add"/>"/>
		  		<input class="buttons" type="reset" value="<fmt:message key="cancel"/>"/>
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
	<div id="card" class="information">
		<h4>Parent Card</h4>
	    <p><fmt:message key="project.name"/>: ${parent.name}</p>
	    <p><fmt:message key="project.description"/>: ${parent.description}</p>
	</div>
</div>

</body>
</html>
</page:applyDecorator>