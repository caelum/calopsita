<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>
<html>
<head>
<%@include file="../javascripts.jspf" %>
</head>
<body>
<div id="iteration_cards" class="selectable cards">
  	<h2><fmt:message key="cards"/></h2>
	<calopsita:cards cards="${iteration.cards}" listId="cards_list" classes="board" />  	
</div>
<div id="backlog" class="selectable">
	<h2><fmt:message key="backlog"/></h2>
	<calopsita:cards cards="${otherCards}" listId="backlog_list" classes="board" />  	
</div>
<div id="todo_cards" class="selectable cards">
  	<h2><fmt:message key="toDo"/></h2>
	<calopsita:cards cards="${iteration.todoCards}" listId="todo_list" classes="board" />  	
</div>
<div id="done_cards" class="selectable cards">
  	<h2><fmt:message key="done"/></h2>
	<calopsita:cards cards="${iteration.doneCards}" listId="done_list" classes="board" />  	
</div>
</body>
</html>