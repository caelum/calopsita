<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
  <title><fmt:message key="iteration"/></title>
  
  <script type="text/javascript" src="<c:url value="/javascript/jquery/selectableDraggable.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/javascript/iteration-show.js"/>"></script>
  <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/iteration.css"/>" />
  <script type="text/javascript">
    initialize('<c:url value="/iterations/${iteration.id}/cards/"/>');
  </script>
</head>

<body>

<div id="tab2">
  <%@include file="iteration.jsp" %>
</div>
</body>
</html>