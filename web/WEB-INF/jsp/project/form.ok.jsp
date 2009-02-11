<%@ page pageEncoding="UTF-8" %>
<calopsita:page title="Project" bodyClass="project">

<form id="form" action="project/salva/" method="post">
  <p>Name: <input id="name" type="text" name="project.name"/></p>
  <p>Description: <input id="description" type="text" name="project.description"/></p>
  <p><input class="buttons" type="submit" value="Create"/></p>
</form>

<a href="/calopsita/">Voltar</a>

</calopsita:page>