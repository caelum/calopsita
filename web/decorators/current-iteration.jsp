<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>	
		<title><fmt:message key="iteration"/></title>
		<decorator:head />
	</head>

	<body>
		<div id="content" title="<fmt:message key="iteration.current"/>">
			<c:if test="${not empty iteration}">
				<%@include file="timeline.jsp" %>
			</c:if>
			<decorator:body />
		</div>
	</body>
</html>