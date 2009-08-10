<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
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
		
		<div id="iteration_cards" class="selectable cards">
			<h2><fmt:message key="cards"/></h2>
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
	</div>	  
</body>
</html>
</page:applyDecorator>