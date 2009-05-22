<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project"/></title>
	
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
    <script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/project-show.js"/>"></script>
</head>

<body>

<div id="tab3">
  <ul id="tabnav">
    <li class="tab1"><a href="<c:url value="/iterations/${project.id }/current/"/>">Current iteration</a></li>
    <li class="tab2"><a href="<c:url value="/iterations/${project.id }/list/"/>">Iterations</a></li>
    <li class="tab3"><a href="<c:url value="/project/${project.id }/cards/"/>">Cards</a></li>
    <li class="tab4"><a href="<c:url value="/project/${project.id }/admin/"/>">Admin</a></li>
  </ul>
  
  <div id="projects">
      <p><fmt:message key="project.name"/>: ${project.name}</p>
  </div>
  
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
    		<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="toggle('storyForm');"/>
    	</p>
  </form>
</div>
</body>
</html>