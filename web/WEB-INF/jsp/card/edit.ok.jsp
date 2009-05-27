<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project"/></title>
	<script type="text/javascript" src="<c:url value="/javascript/project-show.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
</head>

<body>

<div id="card">
    <p><fmt:message key="project.name"/>: ${card.name}</p>
    <p><fmt:message key="project.description"/>: ${card.description}</p>
</div>

<form name="editCard" action="<c:url value='/card/update/' />" method="post">
	<input type="hidden" name="card.id" value="${card.id }"/>
	<input type="hidden" name="project.id" value="${card.project.id }"/>
	<p>
		<label><fmt:message key="card.name"/></label>
		<em>*</em><input type="text" name="card.name" value="${card.name }"/>
	</p>
	<p>
		<label><fmt:message key="card.description"/></label>
		<em>*</em><textarea name="card.description" >${card.description }</textarea>
	</p>
	<p>
		<input class="buttons" type="submit" value="<fmt:message key="update"/>"/>
		<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="window.location = $('#back').attr('href')"/>
	</p>
</form>

<div id="cards">
	<c:if test="${not empty cards}">
		<%@include file="../card/update.ok.jsp" %>
	</c:if>
</div>

<a href="javascript:toggle('cardForm'); document.addCard.reset();">Add Subcard</a><br/>

<form id="cardForm" class="hidden" name="addCard" action="<c:url value="/subcard/save/"/>" method="post">
	<input type="hidden" name="card.project.id" value="${card.project.id }" />
	<input type="hidden" name="card.parent.id" value="${card.id }" />
	<p>
		<label><fmt:message key="card.name"/></label>
		<em>*</em><input type="text" name="card.name"/>
	</p>
	<p>
		<label><fmt:message key="card.description"/></label>
		<em>*</em><textarea name="card.description"></textarea>
	</p>
    <p>
    	<input class="buttons" type="submit" value="<fmt:message key="add"/>"/>
  		<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="toggle('cardForm');"/>
  	</p>
</form>

<a id="back" href="<c:url value="/project/${card.project.id }/cards/"/>"><fmt:message key="back"/></a>

</body>
</html>