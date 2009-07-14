<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project"/></title>
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery.validate.min.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/project-cards.js"/>"></script>
	<script type="text/javascript">
	  //<![CDATA[
	  
	  //]]>
	</script>
</head>

<body>

<div id="card" class="information clear">
    <p><fmt:message key="project.name"/>: ${card.name}</p>
    <p><fmt:message key="project.description"/>: ${card.description}</p>
</div>
<div id="page-tabs">
	<ul>
		<li><a href="#editCard"><fmt:message key="card.edit"/></a></li>
		<li><a href="#cards"><fmt:message key="card.subcards"/></a></li>
		<li><a href="#cardForm" onclick="$('#cardForm').clearForm();"><fmt:message key="card.subcard.new"/></a></li>
	</ul>

	<form id="editCard" name="editCard" action="<c:url value="/projects/${card.project.id}/cards/${card.id}/" />" method="post">
		<%@include file="form.jsp" %>
		<p>
			<input class="buttons" type="submit" value="<fmt:message key="update"/>"/>
			<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="window.location = $('#back').attr('href')"/>
		</p>
	</form>
	
	<div id="cards">
		<c:if test="${not empty cards}">
			<%@include file="update.jsp" %>
		</c:if>
	</div>
	
	<form id="cardForm" name="addCard" action="<c:url value="/projects/${card.project.id }/cards/${card.id }/subcards/"/>" method="post">
		<%@include file="form.jsp" %>
	    <p>
	    	<input class="buttons" type="submit" value="<fmt:message key="add"/>"/>
	  		<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="toggle('cardForm');"/>
	  	</p>
	</form>
</div>
<div class="clear">
<a id="back" href="<c:url value="/projects/${card.project.id }/cards/"/>"><fmt:message key="back"/></a>
</div>

</body>
</html>