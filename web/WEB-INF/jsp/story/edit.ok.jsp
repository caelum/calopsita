<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project"/></title>
	
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
</head>

<body>

<div id="story">
    <p><fmt:message key="project.name"/>: ${story.name}</p>
    <p><fmt:message key="project.description"/>: ${story.description}</p>
</div>

<form name="editStory" 
	action="<c:url value='/story/update/' />" method="post">
	<input type="hidden" name="story.id" value="${story.id }"/>
	<input type="hidden" name="project.id" value="${story.project.id }"/>
	<p>
		<label><fmt:message key="story.name"/></label>
		<em>*</em><input type="text" name="story.name" value="${story.name }"/>
	</p>
	<p>
		<label><fmt:message key="story.description"/></label>
		<em>*</em><textarea name="story.description" >${story.description }</textarea>
	</p>
	<p>
		<input class="buttons" type="submit" value="<fmt:message key="update"/>"/>
		<input class="buttons" type="button" value="<fmt:message key="cancel"/>" onclick="$('#back').click()"/>
	</p>
</form>

<a id="back" href="<c:url value="/project/${story.project.id }/show/"/>"><fmt:message key="back"/></a>

</body>
</html>