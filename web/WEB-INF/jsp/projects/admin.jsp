<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project"/></title>
	<script type="text/javascript" src="<c:url value="/javascript/common.js"/>"></script>
	<script type="text/javascript">
	function toggleDescription() {
		$('.description').slideToggle();
	}
	$(function() {
		ajaxLoad('<c:url value="/projects/${project.id}/cardType/"/>', "#cardTypes");
	});
	</script>
</head>

<body>

<div id="tab4">
  <div id="projects">
      <p><fmt:message key="project.name"/>: ${project.name}</p>
      <form id="projectForm" action="<c:url value="/projects/${project.id }/"/>" method="post">
  	    <p>
  	    	<fmt:message key="project.description"/>: <span class="description">${fn:escapeXml(project.description)}</span>
  	    	<textarea class="hidden description" name="project.description">${fn:escapeXml(project.description) }</textarea>
  	    </p>
      	<input class="hidden description" type="submit" value="<fmt:message key="edit"/>" />
      	<input class="hidden description" type="reset" value="<fmt:message key="cancel"/>" onclick="toggleDescription()"/>
      </form>
      <a class="description" href="javascript:void(0)" onclick="toggleDescription()">Edit</a>
  </div>
  
  <div id="colaborators">
  	<h2><fmt:message key="colaborators"/>:</h2>
  	<ul>
            <li class="owner">${project.owner.name}</li>
  		<c:forEach items="${project.colaborators}" var="colaborator">
  		  <li>${colaborator.name }</li>
  		</c:forEach>
  	</ul>
  </div>
  
  <a href="javascript:toggle('colaborator')"><fmt:message key="project.addColaborator"/></a><br/>
  <form id="colaborator" class="hidden" name="addColaborator"
    action="<c:url value="/projects/${project.id}/colaborators/"/>" method="post">
    <select name="colaborator.login">
      <c:forEach items="${users}" var="user">
        <option value="${user.login}">${user.name}</option>
      </c:forEach>    
    </select>
    <input type="submit" value="<fmt:message key="add"/>"/>
    <input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="toggle('colaborator');"/>
  </form>
  
  <div id="cardTypes">
  </div>
</div>
</body>
</html>
