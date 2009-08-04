<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>

<page:applyDecorator name="admin">
<html>
<head>
	<title>${project.name}</title>
</head>

<body>

	<div id="tab4">
		<div id="colaborators">
			<ul class="pretty">
				<li class="owner">${project.owner.name}</li>
				<c:forEach items="${project.colaborators}" var="colaborator">
					<li>${colaborator.name }</li>
				</c:forEach>
			</ul>
		  	<div class="clear">
			  <a href="javascript:toggle('colaborator')"><fmt:message key="project.addColaborator"/></a><br /><br />
			  
			  <form id="colaborator" class="hidden" name="addColaborator"
			    action="<c:url value="/projects/${project.id}/colaborators/"/>" method="post">
			    <select name="colaborator.login">
			      <c:forEach items="${project.unrelatedUsers}" var="user">
			        <option value="${user.login}">${user.name}</option>
			      </c:forEach>    
			    </select>
			    <input type="submit" value="<fmt:message key="add"/>"/>
			    <input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="toggle('colaborator');"/>
			  </form>
		  	</div>
		  </div>
	</div>
</body>
</html>
</page:applyDecorator>