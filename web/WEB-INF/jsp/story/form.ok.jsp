<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Story" bodyClass="story">

<form name="addStory" action="<c:url value="/story/save/"/>" method="post">
  <p>Name: <input type="text" name="story.name"/></p>
  <p>Description: <input type="text" name="story.description"/></p>
  <p><input class="buttons" type="submit" value="Create"/></p>
</form>

<a href="<c:url value="/"/>">Back</a>

</calopsita:page>