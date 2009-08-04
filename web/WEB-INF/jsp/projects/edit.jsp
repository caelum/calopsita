<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>

<page:applyDecorator name="admin">
<html>
<head>
	<title>${project.name}</title>
</head>

<body>

	<div id="tab4">
		<div id="projects">
			<p><fmt:message key="project.name"/>: ${project.name}</p>
			<form id="projectForm" action="<c:url value="/projects/${project.id }/"/>" method="post">
				<p>
					<fmt:message key="project.description"/>: 
					<textarea class="description" name="project.description">${fn:escapeXml(project.description) }</textarea>
				</p>
				<input class="description" type="submit" value="<fmt:message key="edit"/>" />
				<input class="description" type="reset" value="<fmt:message key="cancel"/>"/>
			</form>
		</div>
	</div>
</body>
</html>
</page:applyDecorator>