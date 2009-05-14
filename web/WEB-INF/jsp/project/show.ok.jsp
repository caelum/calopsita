<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project"/></title>
	
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
    <script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/project-show.js"/>"></script>
	<script type="text/javascript">
		initialize('<fmt:message key="validation.dateRange"/>');
	</script>
</head>

<body>

<div id="project">
    <p><fmt:message key="project.name"/>: ${project.name}</p>
    <p><fmt:message key="project.description"/>: ${project.description}</p>
</div>

<div id="colaborators">
	<h2><fmt:message key="colaborators"/>:</h2>
	<ul>
          <li class="owner">${project.owner.name}</li>
		<c:forEach items="${project.colaborators}" var="colaborator">
		  <li>${colaborator.name }</li>
		</c:forEach>
	</ul>
</div>
  
<c:if test="${not empty project.iterations}">
  <div id="iterations">
    <h2><fmt:message key="iterations"/>:</h2>
    <ul>
      <c:forEach items="${project.iterations}" var="iteration">
        <li id="${iteration.current ? 'current' : ''}">
			<a href="<c:url value="/iteration/${iteration.id}/show/"/>">${iteration.goal}</a>
			<c:if test="${not empty iteration.endDate }" >
				(<fmt:message key="dueDate"/> ${iteration.endDate })
			</c:if>
			<c:if test="${iteration.startable }">
				<a name="start ${iteration.goal }" href="<c:url value="/iteration/${iteration.id }/start/"/>"><fmt:message key="start" /></a>
			</c:if>
            <a class="delete" name="delete ${iteration.goal }" href="javascript:void(0)"
                onclick="confirmIterationDeletion('<c:url value="/iteration/${iteration.id}/delete/"/>')">X</a>
		</li>
      </c:forEach>
    </ul>
  </div>
</c:if>


<div id="stories">
	<c:if test="${not empty stories}">
		<%@include file="../story/update.ok.jsp" %>
	</c:if>
</div>
<a href="javascript:toggle('storyForm'); document.addStory.reset();"><fmt:message key="project.addStory"/></a><br/>

<form id="storyForm" class="hidden" name="addStory" action="<c:url value="/story/save/"/>" method="post">
	<input type="hidden" name="project.id" value="${project.id }" />
	<p>
		<label><fmt:message key="story.name"/></label>
		<em>*</em><input type="text" name="story.name"/>
	</p>
	<p>
		<label><fmt:message key="story.description"/></label>
		<em>*</em><textarea name="story.description"></textarea>
	</p>
    <p>
    	<input class="buttons" type="submit" value="<fmt:message key="add"/>"/>
  		<input class="buttons" type="button" value="<fmt:message key="cancel"/>" onclick="document.addStory.reset(); toggle('storyForm');"/>
  	</p>
</form>

<a href="javascript:toggle('colaborator')"><fmt:message key="project.addColaborator"/></a><br/>
<form id="colaborator" class="hidden" name="addColaborator" 
	action="<c:url value="/add/colaborator/"/>" method="post">
	<input type="hidden" name="project.id" value="${project.id }" />
	<select name="colaborator.login">
		<c:forEach items="${users}" var="user">
			<option value="${user.login}">${user.name}</option>
		</c:forEach>		
	</select>
	<input type="submit" value="<fmt:message key="add"/>"/>
	<input class="buttons" type="button" value="<fmt:message key="cancel"/>" onclick="toggle('colaborator');"/>
</form>

<a href="javascript:toggle('iteration'); document.addIteration.reset();"><fmt:message key="project.addIteration"/></a><br/>
<form id="iteration" class="hidden" name="addIteration" action="<c:url value="/iteration/save/"/>" method="post">
  	<input type="hidden" name="project.id" value="${project.id }" />
   	<p>
		<label><fmt:message key="iteration.goal"/></label>
		<em>*</em><input type="text" name="iteration.goal"/>
	</p>
	<p>
		<label><fmt:message key="iteration.startDate"/></label>
		<em></em><input type="text" name="iteration.startDate" class="datepicker"/>
	</p>
	<p>
		<label><fmt:message key="iteration.endDate"/></label>
		<em></em><input type="text" name="iteration.endDate" class="datepicker"/>
	</p>
	<p>
		<input type="submit" value="<fmt:message key="add"/>"/>
	 	<input class="buttons" type="button" value="<fmt:message key="cancel"/>" onclick="toggle('iteration'); document.addIteration.reset();"/>
	</p>
</form>
<a href="<c:url value="/"/>"><fmt:message key="back"/></a>

</body>
</html>