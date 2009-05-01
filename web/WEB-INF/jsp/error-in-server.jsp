<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isErrorPage="true"%>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/error.css"/>" />

<div id="error">
	<div id="logo">
		<a href="<c:url value="/"/>">
			<img src="<c:url value="/images/logo.png"/>" alt="calopsita" />
		</a>
	</div>
	
	<span>
		<fmt:message key="error.server"/>
	</span>

	<p>
		${pageContext.errorData.throwable }
	</p>
</div>
