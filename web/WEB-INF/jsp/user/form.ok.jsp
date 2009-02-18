<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="User" bodyClass="user">

<form id="form" action="user/save/" method="post">
  <p>Name: <input type="text" name="user.name"/></p>
  <p>Login: <input type="text" name="user.login"/></p>
  <p>Email: <input type="text" name="user.email"/></p>
  <p>Password: <input type="password" name="user.password"/></p>
  <p>Confirmation: <input type="password" name="user.confirmation"/></p>
  <p><input class="buttons" type="submit" value="Sign Up"/></p>
</form>

<a href="<c:url value="/"/>">Voltar</a>

</calopsita:page>