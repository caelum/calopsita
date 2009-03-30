<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Project" bodyClass="project">

<form name="addProject" action="<c:url value="/project/save/"/>" method="post">
  <p>Name: <input type="text" name="project.name"/></p>
  <p>Description: <textarea name="project.description"></textarea></p>
  <p><input class="buttons" type="submit" value="Create"/></p>
</form>

<a href="<c:url value="/"/>">Back</a>

</calopsita:page>