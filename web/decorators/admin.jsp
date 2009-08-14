<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>
<html>
	
	<head>
		<decorator:head />
	</head>
	<body>
		<calopsita:sub-menu>
			<calopsita:sub-menu-item uri="/projects/${project.id}/edit/" message="edit"/>
			<calopsita:sub-menu-item uri="/projects/${project.id}/colaborators" message="colaborators"/>
			<calopsita:sub-menu-item uri="/projects/${project.id}/cardTypes/" message="cardTypes"/>
		</calopsita:sub-menu>
	
		<decorator:body />
	</body>
</html>