<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="User" bodyClass="user" javascript="/javascript/jquery.validate.min.js,/javascript/user.js" css="/css/user.css">

<form id="form" action="<c:url value="/user/login/"/>" method="post">
  <p>
    <label for="user.login">Login</label>
    <input type="text" name="user.login">
  </p>
  <p>
    <label for="user.password">Password</label>
    <input type="password" id="password" name="user.password"/>
  </p>
  <p>
    <label for="user.remember">Remember me</label>
    <input type="checkbox" name="user.remember"/>
  <p>
    <input class="submit" type="submit" value="Sign Up"/>
  </p>
</form>

<a href="<c:url value="/"/>">Voltar</a>

</calopsita:page>