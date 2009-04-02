<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Home" bodyClass="home" css="/css/user.css">

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
    <p>
      <input class="submit" type="submit" value="Sign In"/>
      or <a href="<c:url value="/user/formSignUp/"/>">Sign Up</a>
    </p>
  </form>
</div>

</calopsita:page>