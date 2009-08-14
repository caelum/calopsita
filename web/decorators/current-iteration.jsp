<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>	
		<title><fmt:message key="iteration"/></title>
		<decorator:head />
	</head>

	<body>

		<calopsita:sub-menu>
			<calopsita:sub-menu-item uri="/projects/${project.id}/iterations/current/" message="iteration.current" />
			<c:if test="${not empty iteration.id}">
				<calopsita:sub-menu-item uri="/projects/${project.id}/iterations/${iteration.id}/edit/" message="edit" />
				<calopsita:sub-menu-item uri="/projects/${project.id}/iterations/${iteration.id}/" message="iteration.plan" />
			</c:if>
		</calopsita:sub-menu>
		<c:if test="${not empty iteration}">
			<%@include file="timeline.jsp" %>
		</c:if>
		<decorator:body />
	
	</body>
</html>