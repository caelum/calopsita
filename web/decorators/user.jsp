<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<page:applyDecorator name="calopsita">
	<html xmlns="http://www.w3.org/1999/xhtml">
		<head>	
		    <title><decorator:title default="Home" /> - Calopsita - Agile teams project management tool</title>
	
			<decorator:head />
		</head>
		
		<body>
			
			<hr class="separador"/>
			
			<decorator:body />
		
		</body>
	</html>
</page:applyDecorator>