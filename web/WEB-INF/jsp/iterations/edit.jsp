<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<page:applyDecorator name="current-iteration">
<html>
<head>
<script type="text/javascript" src="<c:url value="/javascript/iteration-edit.js"/>"></script>
	  <script type="text/javascript" src="<c:url value="/javascript/jquery/jquery.validate.min.js"/>"></script>
	  <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/iteration.css"/>" />
</head>
<body>


<div id="tab1">
	<c:if test="${not empty iteration}">
		<div id="form">
			<%@include file="editForm.jsp" %>
		</div>
	</c:if>
	
	<c:if test="${empty iteration}">
		<p>There is no current iteration</p>
	</c:if>
</div>
</body>
</html>
</page:applyDecorator>