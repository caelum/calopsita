<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="user"/></title>
	
	<script type="text/javascript" src="<c:url value="/javascript/user.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/user.css"/>" />
</head>

<body>
	<div id="signUp">
		<fmt:message key="alreadyOnCalopsita"/>? <a href="<c:url value="/"/>"><fmt:message key="user.login"/></a>
	
		<form id="form" name="signUp" action="<c:url value="/user/save/"/>" method="post">
		  <p>
		    <label for="user.name"><fmt:message key="user.name"/></label>
		    <em>*</em><input type="text" name="user.name"/>
		  </p>
		  <p>
		    <fmt:message key="user.login"/>
		    <em>*</em><input type="text" name="user.login">
		  </p>
		  <p>
		    <fmt:message key="user.email"/>
		    <em>*</em><input type="text" name="user.email"/>
		  </p>
		  <p>
		    <fmt:message key="user.password"/>
		    <em>*</em><input type="password" id="password" name="user.password"/>
		  </p>
		  <p>
		    <fmt:message key="user.confirmation"/>
		    <em>*</em><input type="password" name="user.confirmation"/>
		  </p>
		  <p>
		    <input class="submit" type="submit" value="<fmt:message key="user.signUp"/>"/>
		  </p>
		</form>
		
	</div>
</body>
</html>