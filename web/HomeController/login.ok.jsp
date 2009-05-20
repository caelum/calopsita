<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="user.login"/></title>
	
	<script type="text/javascript" src="<c:url value="/javascript/user.js"/>"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/user.css"/>" />
</head>

<body>

<div id="login">
  <form id="form" name="login" action="<c:url value="/user/login/"/>" method="post">
    <p>
      <label><fmt:message key="user.login"/></label>
      <input type="text" name="user.login">
    </p>
    <p>
      <label><fmt:message key="user.password"/></label>
      <input type="password" id="password" name="user.password"/>
    </p>
    <p>
      <input class="submit" type="submit" value="<fmt:message key="user.login"/>"/>
      <fmt:message key="login.or"/> <a href="<c:url value="/user/formSignUp/"/>"><fmt:message key="user.signUp"/></a>
    </p>
  </form>
</div>

</body>
</html>