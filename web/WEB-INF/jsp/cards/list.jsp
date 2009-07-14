<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
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
  <div id="page-tabs">
	<ul>
		<li><a href="#cards"><fmt:message key="cards.all"/></a></li>
		<li><a href="<c:url value="/projects/${project.id }/prioritization/"/>"><fmt:message key="prioritize"/></a></li>
		<li><a href="#cardForm"><fmt:message key="project.addCard"/></a></li>
	</ul>
	  <div id="cards">
	  	<c:if test="${not empty cards}">
	  		<%@include file="../cards/update.jsp" %>
	  	</c:if>
	  </div>
	  <form id="cardForm" name="addCard" action="<c:url value="/projects/${project.id }/cards/"/>" method="post">
	  	<%@include file="form.jsp" %>
		<p>
			<input class="buttons" type="submit" value="<fmt:message key="add"/>" />
			<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="toggle('cardForm');" />
		</p>
	  </form>
  </div>
</div>
</body>
</html>
