<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="projects"/></title>
	
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
</head>

<body>

<a href="<c:url value="/project/form/"/>"><fmt:message key="project.new"/></a>
<div id="projects">
  <ul>
    <c:forEach var="project" items="${projects}">
      <li>
        <p><fmt:message key="project.name"/>: <a href="<c:url value="/project/${project.id}/show/"/>">${project.name}</a></p>
        <p><fmt:message key="project.description"/>: ${project.description}</p>
      </li>
    </c:forEach>
  </ul>
</div>

</body>
</html>