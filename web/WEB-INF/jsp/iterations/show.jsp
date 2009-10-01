<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>
<page:applyDecorator name="iteration">
<html>
<head>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/iteration.css"/>" />
	<script type="text/javascript" src="<c:url value="/javascript/jquery/selectableDraggable.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery.validate.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/iteration-show.js"/>"></script>
</head>

<body>
	<div id="iteration">
		<div class="help">
			<p><fmt:message key="help.addingAndRemovingCards"/></p>
		</div>
		
		<div id="iteration_cards" class="selectable cards column left">
			<h2><fmt:message key="cards"/></h2>
			<calopsita:cards cards="${iteration.cards}" listId="cards_list" classes="board"/>
		</div>
		
		<div id="backlog" class="selectable column right">
			<h2><fmt:message key="backlog"/></h2>
			<calopsita:cards cards="${otherCards}" listId="backlog_list" classes="board"/>
		</div>
	</div>	  
</body>
</html>
</page:applyDecorator>