<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<page:applyDecorator name="calopsita">
	<html xmlns="http://www.w3.org/1999/xhtml">
	
	<head>	
	    <title><decorator:title default="Projects" /> - Calopsita - Agile teams project management tool</title>
	    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
	    <script type="text/javascript">
		  //<![CDATA[
			var selected;		  
		  //]]>
		</script>
	    <decorator:head />
	    <script type="text/javascript">
		  //<![CDATA[
		  	$(function() {
			  	if ($('#content').attr('title')) {
			  		$('#menu li').select(function(c) { return c.text() == $('#content').attr('title'); })
			  			.next('.submenu').addClass('selected'); 
			  	} else {
					$('#menu li:first + .submenu').addClass('selected'); 
			  	}
		  	});
		  //]]>
		</script>
		<script>
			$(function() {
				var url = window.location.toString();
				$('#sub-menu li a').each(function(c, a) {
					var className = '';
					if(url.match(a.getAttribute('href')+"$")) {
						className = 'selected';
					}
		
					a.parentNode.className = className;
				});
			});
		</script>
	</head>
	
	<body>
		<div id="project">
			<fmt:message key="project" />: ${project.name }
		</div>
		<!--<div id="tabs">	
			<ul id="tabnav">
				<li><a href="<c:url value="/projects/${project.id }/iterations/current/"/>">Current iteration</a></li>
				<li><a href="<c:url value="/projects/${project.id }/iterations/"/>">Iterations</a></li>
				<li><a href="<c:url value="/projects/${project.id }/cards/"/>">Cards</a></li>
				<li><a href="<c:url value="/projects/${project.id }/edit/"/>">Admin</a></li>
			</ul>
		</div>
		-->
		${menu }
		<decorator:body />
	
	</body>
	</html>
</page:applyDecorator>