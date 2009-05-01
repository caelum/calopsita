<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project"/></title>
	
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
</head>

<body>

<div id="project">
    <p><fmt:message key="project.name"/>: ${project.name}</p>
    <p><fmt:message key="project.description"/>: ${project.description}</p>
</div>

<c:if test="${not empty project.colaborators}">
	<div id="colaborators">
		<h2><fmt:message key="colaborators"/>:</h2>
		<ul>
			<c:forEach items="${project.colaborators}" var="colaborator">
				<li>${colaborator.name }</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<c:if test="${not empty project.iterations}">
  <div id="iterations">
    <h2><fmt:message key="iterations"/>:</h2>
    <ul>
      <c:forEach items="${project.iterations}" var="iteration">
        <li><a href="<c:url value="/iteration/${iteration.id}/show/"/>">${iteration.goal}</a></li>
      </c:forEach>
    </ul>
  </div>
</c:if>
<div id="stories">
	<c:if test="${not empty stories}">
		<%@include file="../story/update.ok.jsp" %>
	</c:if>
</div>
<a href="javascript:toggle('story'); document.addStory.reset();">Add Story</a><br/>

<div id="story" style="display: none;">
	<form id="addStory" name="addStory" action="<c:url value="/story/save/"/>" method="post">
		<input type="hidden" name="project.id" value="${project.id }" />
		<p>
			<fmt:message key="story.name"/>
			<em>*</em><input type="text" name="story.name"/>
		</p>
		<p>
			<fmt:message key="story.description"/>
			<em>*</em><textarea name="story.description"></textarea>
		</p>
	    <p>
	    	<input class="buttons" type="submit" value="<fmt:message key="submit"/>"/>
	  		<input class="buttons" type="button" value="<fmt:message key="cancel"/>" onclick="document.addStory.reset(); toggle('story');"/>
	  	</p>
	</form>
</div>
<a href="javascript:toggle('colaborator')"><fmt:message key="project.addColaborator"/></a><br/>
<div id="colaborator" style="display: none;">
	<form id="addColaborator" name="addColaborator" action="<c:url value="/add/colaborator/"/>" method="post">
		<input type="hidden" name="project.id" value="${project.id }" />
		<select name="colaborator.login">
			<c:forEach items="${users}" var="user">
				<option value="${user.login}">${user.name}</option>
			</c:forEach>		
		</select>
		<input type="submit" value="<fmt:message key="add"/>"/>
		<input class="buttons" type="button" value="<fmt:message key="cancel"/>" onclick="toggle('colaborator');"/>
	</form>
</div>

<a href="javascript:toggle('iteration'); document.addIteration.reset();"><fmt:message key="project.addIteration"/></a><br/>
<div id="iteration" style="display: none;">
  <form id="addIteration" name="addIteration" action="<c:url value="/iteration/save/"/>" method="post">
    <input type="hidden" name="project.id" value="${project.id }" />
    <p>
		<fmt:message key="iteration.goal"/>
		<em>*</em><input type="text" name="iteration.goal"/>
	</p>
	<p>
		<fmt:message key="iteration.startDate"/>
		<em></em><input type="text" name="iteration.startDate" class="datepicker"/>
	</p>
	<p>
		<fmt:message key="iteration.endDate"/>
		<em></em><input type="text" name="iteration.endDate" class="datepicker"/>
	</p>
	<p>
		<input type="submit" value="<fmt:message key="add"/>"/>
	 	<input class="buttons" type="button" value="<fmt:message key="cancel"/>" onclick="toggle('iteration'); document.addIteration.reset();"/>
	</p>
  </form>
</div>
<a href="<c:url value="/"/>"><fmt:message key="back"/></a>

</body>
</html>