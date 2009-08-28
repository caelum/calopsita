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
			  		$('#menu > li').each(function(c, e) {
				  		if ($(e).text() == $('#content').attr('title')) {
				  			$($('a', e).attr('href')).addClass('selected'); 
				  		}
			  		});
			  	} else {
					$('#menu .submenu:first').addClass('selected'); 
			  	}
				var url = window.location.href;
				$('.submenu a').each(function(c, a) {
					var className = '';
					if(url.match(a.href +'$')) {
						className = 'selected';
					}
					a.parentNode.className = className;
				});
		  	});
		  //]]>
		</script>
	</head>
	
	<body>
		<div id="project">
			<fmt:message key="project" />: ${project.name }
		</div>
		${menu }
		<decorator:body />
	
	</body>
	</html>
</page:applyDecorator>