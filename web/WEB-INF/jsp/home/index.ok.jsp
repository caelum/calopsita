<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Home" bodyClass="home" css="/css/index.css">

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
      <input class="submit" type="submit" value="Sign Up"/>
    </p>
  </form>
</div>

</calopsita:page>