<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>
<html>
	
	<head>
		<decorator:head />
	</head>
	<body>
		<calopsita:sub-menu>
			<calopsita:sub-menu-item uri="/projects/${project.id}/cards/" message="cards.pending" />
			<calopsita:sub-menu-item uri="/projects/${project.id}/cards/all/" message="cards.all" />
			<calopsita:sub-menu-item uri="/projects/${project.id}/prioritization/" message="prioritize" />
			<calopsita:sub-menu-item uri="/projects/${project.id}/cards/new/" message="project.addCard" />
		</calopsita:sub-menu>
		
		<decorator:body />
	</body>
</html>