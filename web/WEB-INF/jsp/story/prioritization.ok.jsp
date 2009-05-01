<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title>Project priorization</title>
	
	<script type="text/javascript" src="<c:url value="/javascript/iteration.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/iteration.css"/>" />
</head>

<body>

<script type="text/javascript">
var stories = [];
<c:set var="newline" value="
"/>
<c:forEach items="${stories}" var="s" varStatus="status">
	stories.push({
		priority: '${s.priority}',
		name: '${s.name}',
		description: '${fn:replace(s.description, newline, "<br/>")}',
		id: ${s.id}, 
		count: ${status.count} - 1   
	});
</c:forEach>
</script>

	<div id="project">
    	<p>Name: ${project.name}</p>
    	<p>Description: ${project.description}</p>
	</div>
	
	<form id="prioritizationForm" action="<c:url value="/story/prioritize/"/>" method="post">
		<input type="submit" value="Save Priorization" />
		<input type="hidden" name="project.id" value="${project.id }" />
		<div class="table">
			<div class="title">Infinity Priority</div>
			<div id="level_0" class="board" title="Infinity Priority" priority="0"></div>
		</div>

		<div id="board">
			
		</div>

		<div class="title">New Priority Level</div>

		<div id="lowerPriority" class="board" title="New Priority Level"></div>
	</form>

	<a href="<c:url value="/project/${project.id }/show/"/>">Back</a>

</body>
</html>