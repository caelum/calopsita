<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<page:applyDecorator name="calopsita">
	<html xmlns="http://www.w3.org/1999/xhtml">
	
	<head>	
	    <title><decorator:title default="Home" /> - Calopsita - Agile teams project management tool</title>
	    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
	    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
	    <script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>
	    <script type="text/javascript" src="<c:url value="/javascript/project-show.js"/>"></script>
		<decorator:head />
	</head>
	
	<body>
	
		<div id="tabs">	
			<ul id="tabnav">
				<li class="tab1"><a href="<c:url value="/projects/${project.id }/iterations/current/"/>">Current iteration</a></li>
				<li class="tab2"><a href="<c:url value="/projects/${project.id }/iterations/"/>">Iterations</a></li>
				<li class="tab3"><a href="<c:url value="/projects/${project.id }/cards/"/>">Cards</a></li>
				<li class="tab4"><a href="<c:url value="/projects/${project.id }/admin/"/>">Admin</a></li>
			</ul>
		</div>
		<decorator:body />
	
	</body>
	</html>
</page:applyDecorator>