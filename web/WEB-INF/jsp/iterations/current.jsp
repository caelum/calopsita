<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>
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
			<calopsita:cards cards="${iteration.todoCards}" classes="board" listId="todo_list" />
		</div>
		
		<c:forEach items="${project.columns}" var="column">
			<div id="${column.name}" class="selectable cards column">
				<h2>${column.name }</h2>
				<calopsita:cards cards="${column.cards}" classes="board" listId="${column.name }_list" />
			</div>
		</c:forEach>
		
		<div id="done_cards" class="selectable cards column">
			<h2><fmt:message key="done"/></h2>
			<calopsita:cards cards="${iteration.doneCards}" classes="board" listId="done_list" />
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