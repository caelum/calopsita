<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>

	<calopsita:sub-menu>
		<calopsita:sub-menu-item uri="/projects/${project.id}/iterations/" message="iterations.all" />
		<calopsita:sub-menu-item uri="/projects/${project.id}/iterations/new/" message="project.addIteration" />
	</calopsita:sub-menu>
	
	<decorator:body />
