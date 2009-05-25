<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
  <ul id="tabnav">
    <li class="tab1"><a href="<c:url value="/iterations/${project.id }/current/"/>">Current iteration</a></li>
    <li class="tab2"><a href="<c:url value="/iterations/${project.id }/list/"/>">Iterations</a></li>
    <li class="tab3"><a href="<c:url value="/project/${project.id }/cards/"/>">Cards</a></li>
    <li class="tab4"><a href="<c:url value="/project/${project.id }/admin/"/>">Admin</a></li>
  </ul>
  
    <div id="projects">
        <p><fmt:message key="project.name"/>: ${project.name}</p>
    </div>
  
  <c:if test="${not empty iteration}">
    <div id="iteration_text">
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
  
  <div id="help" class="dialog" title="Adding and Removing Cards">
  	<fmt:message key="iteration.help.addingAndRemovingCards"/>
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
  <div id="backlog" class="selectable clear">
  	<h2><fmt:message key="backlog"/></h2>
  
  	<ol id="backlog_list" class="board">
  		<c:forEach items="${otherCards}" var="card" varStatus="s">
  			<c:set var="cardId">backlog</c:set>
  			<%@include file="storyCard.jsp" %>
  		</c:forEach>
  	</ol>
  </div>
</c:if>
<c:if test="${empty iteration}">
<p>There is no current iteration</p>
</c:if>
