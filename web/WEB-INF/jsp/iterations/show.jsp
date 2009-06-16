<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
</head>

<body>

<div id="tab2">
  <a href="javascript:toggle('iteration');"><fmt:message key="edit"/></a><br/>
  <%@include file="editForm.jsp" %>
  
  <div id="help" class="dialog" title="Adding and Removing Cards">
  	<fmt:message key="iteration.help.addingAndRemovingCards"/>
  </div>
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

</div>
</body>
</html>