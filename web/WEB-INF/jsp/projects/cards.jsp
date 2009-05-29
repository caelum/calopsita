<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project"/></title>
	
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
    <script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/project-show.js"/>"></script>
</head>

<body>

<div id="tab3">
  <%@include file="tabs.jsp" %>
  
  <div id="projects">
      <p><fmt:message key="project.name"/>: ${project.name}</p>
  </div>
  
  <div id="cards">
  	<c:if test="${not empty cards}">
  		<%@include file="../cards/update.ok.jsp" %>
  	</c:if>
  </div>
  <a href="javascript:toggle('cardForm'); document.addCard.reset();"><fmt:message key="project.addCard"/></a><br/>
  
  <form id="cardForm" class="hidden" name="addCard" action="<c:url value="/cards/"/>" method="post">
  	<input type="hidden" name="project.id" value="${project.id }" />
  	<p>
  		<label><fmt:message key="card.name"/></label>
  		<em>*</em><input type="text" name="card.name"/>
  	</p>
  	<p>
  		<label><fmt:message key="card.description"/></label>
  		<em>*</em><textarea name="card.description"></textarea>
  	</p>
  	<fieldset title="<fmt:message key="gadgets" />">
  		<legend><fmt:message key="gadgets" /></legend>
  		<input type="checkbox" name="gadgets[0]" value="PRIORITIZATION" id="PRIORITIZATION" /><fmt:message key="PRIORITIZATION" />
  	</fieldset>
	<p>
		<input class="buttons" type="submit" value="<fmt:message key="add"/>" />
		<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="toggle('cardForm');" />
	</p>
</form>
</div>
</body>
</html>
