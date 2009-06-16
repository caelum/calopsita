<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="../javascripts.jspf" %>
</head>
<body>
<div id="iteration_cards" class="selectable cards">
  	<h2><fmt:message key="cards"/> <a href="#" onclick="return show_help()">?</a></h2>
  	<ol id="cards_list" class="board">
  		<c:forEach items="${iteration.cards}" var="card" varStatus="s">
  			<c:set var="cardId">cards</c:set>
  			<%@include file="storyCard.jsp" %>
  		</c:forEach>
  	</ol>
  </div>
<div id="backlog" class="selectable">
	<h2><fmt:message key="backlog"/></h2>

	<ol id="backlog_list" class="board">
		<c:forEach items="${otherCards}" var="card" varStatus="s">
			<c:set var="cardId">backlog</c:set>
			<%@include file="storyCard.jsp" %>
		</c:forEach>
	</ol>
</div>
<div id="todo_cards" class="selectable cards">
  	<h2><fmt:message key="toDo"/> <a href="#" onclick="return show_help()">?</a></h2>
  	<ol id="todo_list" class="board">
  		<c:forEach items="${iteration.todoCards}" var="card" varStatus="s">
  			<c:set var="cardId">cards</c:set>
  			<%@include file="storyCard.jsp" %>
  		</c:forEach>
  	</ol>
  </div>
  <div id="done_cards" class="selectable cards">
  	<h2><fmt:message key="done"/> <a href="#" onclick="return show_help()">?</a></h2>
  	<ol id="done_list" class="board">
  		<c:forEach items="${iteration.doneCards}" var="card" varStatus="s">
  			<c:set var="cardId">done</c:set>
  			<%@include file="storyCard.jsp" %>
  		</c:forEach>
  	</ol>
  </div>
</body>
</html>