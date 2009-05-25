<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="projects"/></title>
	<script type="text/javascript" src="<c:url value="/javascript/project-list.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
</head>

<body>

<div class="hidden" id="deletion_text"><fmt:message key="delete.project.confirmation"></fmt:message></div>
<a href="<c:url value="/project/form/"/>"><fmt:message key="project.new"/></a>
<div id="projects">
  <ul>
    <c:forEach var="project" items="${projects}">
      <li>
        <p><fmt:message key="project.name"/>: <a href="<c:url value="/iterations/${project.id}/current/"/>">${project.name}</a>
        <c:if test="${currentUser eq project.owner}">
	        <a class="delete" name="delete ${project.name }" href="javascript:void(0)"
				onclick="confirmProjectDeletion('<c:url value="/project/${project.id }/delete/"/>')">X</a>
        </c:if>
        <p><fmt:message key="project.description"/>: ${project.description}</p>
      </li>
    </c:forEach>
  </ul>
</div>
</body>
</html>