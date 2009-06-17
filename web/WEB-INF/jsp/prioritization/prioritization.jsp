<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project.priorization"/></title>
	<script type="text/javascript" src="<c:url value="/javascript/jquery/selectableDraggable.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/card-prioritization.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/iteration.css"/>" />
	<script type="text/javascript">
		initialize('<fmt:message key="infinityPriority"/>', '<c:url value="/projects/${project.id }/prioritize/"/>');
	</script>
</head>

<body>

	<div id="project">
    	<p><fmt:message key="project.name"/>: ${project.name}</p>
    	<p><fmt:message key="project.description"/>: ${project.description}</p>
	</div>
	
	<div id="prioritizationForm">
		<div id="board">
			<c:forEach items="${cards}" varStatus="s" var="currentPriority" >
				<h2 id="title_${s.index }" class="title"><fmt:message key="priority"/> ${s.index }</h2>
				<ul id="level_${s.index }" class="board" title="Priority ${s.index }" priority="${s.index }">
					<c:forEach items="${currentPriority}" var="card">
						<li class="card" name="${card.name }" title="Double click for description" 
							ondblclick="showDialog('${card.name}', 'description_${card.id }')">
							<p>${card.name }</p>
							<pre id="description_${card.id }" class="hidden">${fn:escapeXml(card.description)}</pre>
							<input type="hidden" name="cards[#].id" value="${card.id}" />
							<input class="priority" type="hidden" name="cards[#].priority" value="${s.index }" />
						</li>
					</c:forEach>
				</ul>
			</c:forEach>
		</div>

		<h2 class="title"><fmt:message key="newPriorityLevel"/></h2>
		<ul id="lowerPriority" class="board" title="New Priority Level"></ul>
	</div>

	<form id="undoForm" action="<c:url value="/projects/${project.id }/prioritize/"/>" method="POST">
		<c:forEach items="${cards}" var="currentPriority" varStatus="s">
			<c:forEach items="${currentPriority}" var="card">
				<div class="undo hidden">
					<input type="hidden" name="cards[#].id" value="${card.id}" />
					<input class="priority" type="hidden" name="cards[#].priority" value="${s.index}" />
				</div>
			</c:forEach>
		</c:forEach>
		<input type="submit" value="<fmt:message key='undo'/>"/>		
	</form>
	<a href="<c:url value="/projects/${project.id }/cards/"/>"><fmt:message key="back"/></a>

</body>
</html>