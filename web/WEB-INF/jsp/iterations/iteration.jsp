<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">
$(document).ready(function() {
    var daysBetweenTodayAndStartDate = (parseInt('${today.dayOfYear}') + 365 * parseInt('${today.year}')) - (parseInt('${iteration.startDate.dayOfYear}') + 365 * parseInt('${iteration.startDate.year}'));
    var daysBetweenEndDateAndToday = (parseInt('${iteration.endDate.dayOfYear}') + 365 * parseInt('${iteration.endDate.year}')) - (parseInt('${today.dayOfYear}') + 365 * parseInt('${today.year}'));
    var daysBetweenEndDateAndStartDate = (parseInt('${iteration.endDate.dayOfYear}') + 365 * parseInt('${iteration.endDate.year}')) - (parseInt('${iteration.startDate.dayOfYear}') + 365 * parseInt('${iteration.startDate.year}'));
    $('#before_today_line').css( {
        width : 600 * daysBetweenTodayAndStartDate  / daysBetweenEndDateAndStartDate
    });
    $('#after_today_line').css( {
        width : 600 * daysBetweenEndDateAndToday  / daysBetweenEndDateAndStartDate
    })
});
</script>

	<div id="projects">
	    <p><fmt:message key="project.name"/>: ${project.name}</p>
	</div>
	
  <c:if test="${not empty iteration}">
	<div id="timeline">
		<div id="begin_date" class="date" >
			<div class="year">${iteration.startDate.year }</div>
			<div class="day">${iteration.startDate.dayOfMonth }</div>
			<div class="month"><joda:format value="${iteration.startDate}" pattern="MMM" /></div>
		</div>
		
		<hr class="line" id="before_today_line" />
		
		<div id="today" class="date" >
			<div class="year">${today.year }</div>
			<div class="day">${today.dayOfMonth }</div>
			<div class="month"><joda:format value="${today}" pattern="MMM" /></div>
		</div>
		
		<hr class="line" id="after_today_line" />
		
		<div id="end_date" class="date" >
			<div class="year">${iteration.endDate.year }</div>
			<div class="day">${iteration.endDate.dayOfMonth }</div>
			<div class="month"><joda:format value="${iteration.endDate}" pattern="MMM" /></div>
		</div>
	</div>
  
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
