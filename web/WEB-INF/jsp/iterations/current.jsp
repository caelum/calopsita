<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
<script type="text/javascript" src="<c:url value="/javascript/iteration-show.js"/>"></script>
	  <script type="text/javascript" src="<c:url value="/javascript/jquery/selectableDraggable.js"/>"></script>
	  <script type="text/javascript" src="<c:url value="/javascript/jquery/jquery.validate.min.js"/>"></script>
	  <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/iteration.css"/>" />
</head>
<body>

<div id="tab1">
<c:if test="${not empty iteration}">
  <a href="javascript:toggle('iteration');"><fmt:message key="edit"/></a><br/>
  <%@include file="editForm.jsp" %>
  
  <div class="help">
  	<p><fmt:message key="help.changeCardsStatus"/></p>
  </div>
  <div id="todo_cards" class="selectable cards">
  	<h2><fmt:message key="toDo"/></h2>
  	<ol id="todo_list" class="board">
  		<c:forEach items="${iteration.todoCards}" var="card" varStatus="s">
  			<c:set var="cardId">cards</c:set>
  			<%@include file="storyCard.jsp" %>
  		</c:forEach>
  	</ol>
  </div>
  <div id="done_cards" class="selectable cards">
  	<h2><fmt:message key="done"/></h2>
  	<ol id="done_list" class="board">
  		<c:forEach items="${iteration.doneCards}" var="card" varStatus="s">
  			<c:set var="cardId">done</c:set>
  			<%@include file="storyCard.jsp" %>
  		</c:forEach>
  	</ol>
  </div>
</c:if>
<c:if test="${empty iteration}">
<p>There is no current iteration</p>
</c:if>

</div>
</body>
</html>
