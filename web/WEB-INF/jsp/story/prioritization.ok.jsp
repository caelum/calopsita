<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project.priorization"/></title>
	
	<script type="text/javascript" src="<c:url value="/javascript/story-prioritization.js"/>"></script>
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
    	<p><fmt:message key="project.name"/>: ${project.name}</p>
    	<p><fmt:message key="project.description"/>: ${project.description}</p>
	</div>
	
	<form id="prioritizationForm" action="<c:url value="/story/prioritize/"/>" method="post">
		<input type="submit" value="Save Priorization" />
		<input type="hidden" name="project.id" value="${project.id }" />
		<div class="table">
			<div class="title"><fmt:message key="infinityPriority"/></div>
			<div id="level_0" class="board" title="Infinity Priority" priority="0"></div>
		</div>

		<div id="board">
			
		</div>

		<div class="title"><fmt:message key="newPriorityLevel"/></div>

		<div id="lowerPriority" class="board" title="New Priority Level"></div>
	</form>

	<a href="<c:url value="/project/${project.id }/show/"/>"><fmt:message key="back"/></a>

</body>
</html>