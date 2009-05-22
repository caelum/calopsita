<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="iteration"/></title>
	
	<script type="text/javascript" src="<c:url value="/javascript/jquery/selectableDraggable.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/iteration-show.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/iteration.css"/>" />
	<script type="text/javascript">
		initialize(${iteration.id}, '<c:url value="/iteration/updateStories/"/>', '<c:url value="/iteration/removeStories/"/>');
	</script>
</head>

<body>

<div id="tab1">
  <ul id="tabnav">
    <li class="tab1"><a href="<c:url value="/iterations/${iteration.project.id }/current/"/>">Current iteration</a></li>
    <li class="tab2"><a href="<c:url value="/iterations/${iteration.project.id }/list/"/>">Iterations</a></li>
    <li class="tab3"><a href="<c:url value="/project/${iteration.project.id }/cards/"/>">Cards</a></li>
    <li class="tab4"><a href="<c:url value="/project/${iteration.project.id }/admin/"/>">Admin</a></li>
  </ul>
  
  <c:if test="${not empty iteration}">
    <div id="projects">
        <p><fmt:message key="project.name"/>: ${iteration.project.name}</p>
    </div>
  
    <div id="iteration_text">
      <p><fmt:message key="iteration.current"/></p>
    
      <p><fmt:message key="iteration.goal"/>: ${iteration.goal}</p>
      <c:if test="${not empty iteration.startDate}">
  	    <p><fmt:message key="iteration.startDate"/>: ${iteration.formattedStartDate}</p>
      </c:if>
      <c:if test="${not empty iteration.endDate}">
  	    <p><fmt:message key="iteration.endDate"/>: ${iteration.formattedEndDate}</p>
      </c:if>
  </div>
  <a href="javascript:toggle('iteration');"><fmt:message key="edit"/></a><br/>
  <%@include file="editForm.jsp" %>
  
  <div id="help" class="dialog" title="Adding and Removing Stories">
  	<fmt:message key="iteration.help.addingAndRemovingStories"/>
  </div>
  <div id="todo_stories" class="selectable stories">
  	<h2><fmt:message key="toDo"/> <a href="#" onclick="return show_help()">?</a></h2>
  	<ol id="todo_list" class="board">
  		<c:forEach items="${iteration.todoStories}" var="story" varStatus="s">
  			<c:set var="storyId">stories</c:set>
  			<%@include file="storyCard.jsp" %>
  		</c:forEach>
  	</ol>
  </div>
  <div id="done_stories" class="selectable stories">
  	<h2><fmt:message key="done"/> <a href="#" onclick="return show_help()">?</a></h2>
  	<ol id="done_list" class="board">
  		<c:forEach items="${iteration.doneStories}" var="story" varStatus="s">
  			<c:set var="storyId">done</c:set>
  			<%@include file="storyCard.jsp" %>
  		</c:forEach>
  	</ol>
  </div>
  <div id="backlog" class="selectable">
  	<h2><fmt:message key="backlog"/></h2>
  
  	<ol id="backlog_list" class="board">
  		<c:forEach items="${otherStories}" var="story" varStatus="s">
  			<c:set var="storyId">backlog</c:set>
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