<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title>Login</title>
	
	<script type="text/javascript" src="<c:url value="/javascript/user.js"/>" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/user.css"/>" />
</head>

<body>

<div id="login">
  <form id="form" name="login" action="<c:url value="/user/login/"/>" method="post">
    <p>
      <label for="user.login">Login</label>
      <input type="text" name="user.login">
    </p>
    <p>
      <label for="user.password">Password</label>
      <input type="password" id="password" name="user.password"/>
    </p>
    <p>
      <input class="submit" type="submit" value="Sign In"/>
      or <a href="<c:url value="/user/formSignUp/"/>">Sign Up</a>
    </p>
  </form>
</div>

</body>
</html>