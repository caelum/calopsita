<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="iteration"/></title>
	
	<script type="text/javascript" src="<c:url value="/javascript/jquery/selectableDraggable.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/iteration-show.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/iteration.css"/>" />
    <c:if test="${not empty iteration}">
    	<script type="text/javascript">
    		initialize(${iteration.id}, '<c:url value="/iteration/updateCards/"/>', '<c:url value="/iteration/removeCards/"/>');
    	</script>
    </c:if>
</head>

<body>

<div id="tab1">
  <%@include file="iteration.jsp" %>
</div>
</body>
</html>