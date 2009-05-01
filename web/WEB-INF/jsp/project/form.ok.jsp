<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Project" bodyClass="project" javascript="/javascript/project-form.js" css="/css/project.css">

<form id="addProject" name="addProject" action="<c:url value="/project/save/"/>" method="post">
  <p>
  	<label for="project.name">Name</label>
  	<em>*</em><input type="text" name="project.name"/>
  </p>
  <p>
  	<label for="project.description">Description</label>
  	<em>*</em><textarea name="project.description"></textarea>
  </p>
  <p>
  	<input class="submit" type="submit" value="Create"/>
  </p>
</form>

<a href="<c:url value="/"/>">Back</a>

</calopsita:page>