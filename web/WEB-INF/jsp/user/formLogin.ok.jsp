<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="User" bodyClass="user" javascript="/javascript/user.js" css="/css/user.css">

<div id="login">
  <form name="login" action="<c:url value="/user/login/"/>" method="post">
    <p>
      <label for="user.login">Login</label>
      <input type="text" name="user.login">
    </p>
    <p>
      <label for="user.password">Password</label>
      <input type="password" id="password" name="user.password"/>
    </p>
      <input class="submit" type="submit" value="Sign Up"/>
    </p>
  </form>
</div>

<a href="<c:url value="/"/>">Back</a>

</calopsita:page>