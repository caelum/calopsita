<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project"/></title>
</head>

<body>

<div id="tab4">
  <ul id="tabnav">
    <li class="tab1"><a href="<c:url value="/iterations/${project.id }/current/"/>">Current iteration</a></li>
    <li class="tab2"><a href="<c:url value="/iterations/${project.id }/list/"/>">Iterations</a></li>
    <li class="tab3"><a href="<c:url value="/project/${project.id }/cards/"/>">Cards</a></li>
    <li class="tab4"><a href="<c:url value="/project/${project.id }/admin/"/>">Admin</a></li>
  </ul>
  
  <div id="projects">
      <p><fmt:message key="project.name"/>: ${project.name}</p>
      <form id="projectForm" action="<c:url value="/project/${project.id }/update/"/>">
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
</div>
</body>
</html>