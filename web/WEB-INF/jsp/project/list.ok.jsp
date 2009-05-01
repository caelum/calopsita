<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title>Projects</title>
	
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
</head>

<body>

<a href="<c:url value="/project/form/"/>">New Project</a>
<div id="projects">
  <ul>
    <c:forEach var="project" items="${projects}">
      <li>
        <p>Name: <a href="<c:url value="/project/${project.id}/show/"/>">${project.name}</a></p>
        <p>Description: ${project.description}</p>
      </li>
    </c:forEach>
  </ul>
</div>

</body>
</html>