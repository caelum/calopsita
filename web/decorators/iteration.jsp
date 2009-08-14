<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>
<html>
	<head>
		<decorator:head />
	</head>
	<body>
		<calopsita:sub-menu>
			<calopsita:sub-menu-item uri="/projects/${project.id}/iterations/" message="iterations.all" />
			<c:if test="${not empty iteration.id}">
				<calopsita:sub-menu-item uri="/projects/${project.id}/iterations/${iteration.id}/" message="iteration.plan" />
				<calopsita:sub-menu-item uri="/projects/${project.id}/iterations/${iteration.id}/edit/" message="edit" />
			</c:if>
			<calopsita:sub-menu-item uri="/projects/${project.id}/iterations/new/" message="project.addIteration" />
		</calopsita:sub-menu>
		
		<c:if test="${not empty iteration}">
			<%@include file="timeline.jsp" %>
		</c:if>
		
		<decorator:body />
	</body>
</html>