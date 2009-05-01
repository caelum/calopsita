<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project.new"/></title>
	
	<script type="text/javascript" src="<c:url value="/javascript/project-form.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
</head>

<body>

<form id="addProject" name="addProject" action="<c:url value="/project/save/"/>" method="post">
  <p>
  	<fmt:message key="project.name"/>
  	<em>*</em><input type="text" name="project.name"/>
  </p>
  <p>
  	<fmt:message key="project.description"/>
  	<em>*</em><textarea name="project.description"></textarea>
  </p>
  <p>
  	<input class="submit" type="submit" value="<fmt:message key="project.submit"/>"/>
  </p>
</form>

<a href="<c:url value="/"/>"><fmt:message key="back"/></a>

</body>
</html>