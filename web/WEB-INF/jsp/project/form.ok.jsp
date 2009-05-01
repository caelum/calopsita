<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project.new.title"/></title>
	
	<script type="text/javascript" src="<c:url value="/javascript/project-form.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
</head>

<body>

<form id="addProject" name="addProject" action="<c:url value="/project/save/"/>" method="post">
  <p>
  	<label for="project.name">Name</label>
  	<em>*</em><input type="text" name="project.name"/>
  </p>
  <p>
  	<label for="project.description">Description</label>
  	<em>*</em><textarea name="project.description"></textarea>
  </p>
  <p>
  	<input class="submit" type="submit" value="Create"/>
  </p>
</form>

<a href="<c:url value="/"/>">Back</a>

</body>
</html>