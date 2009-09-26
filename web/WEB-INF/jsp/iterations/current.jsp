<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<page:applyDecorator name="current-iteration">
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

	<div id="planning">
		<div class="help">
			<p><fmt:message key="help.changeCardsStatus"/></p>
		</div>
		<div id="todo_cards" class="selectable cards column">
			<h2><fmt:message key="toDo"/></h2>
			<ol id="todo_list" class="board">
				<c:forEach items="${iteration.todoCards}" var="card" varStatus="s">
					<c:set var="cardId">cards</c:set>
					<%@include file="storyCard.jsp" %>
				</c:forEach>
			</ol>
		</div>
		<div id="done_cards" class="selectable cards column">
			<h2><fmt:message key="done"/></h2>
			<ol id="done_list" class="board">
				<c:forEach items="${iteration.doneCards}" var="card" varStatus="s">
					<c:set var="cardId">done</c:set>
					<%@include file="storyCard.jsp" %>
				</c:forEach>
			</ol>
		</div>
	</div>
</c:if>
<c:if test="${empty iteration}">
<p>There is no current iteration</p>
</c:if>

</div>
</body>
</html>
</page:applyDecorator>