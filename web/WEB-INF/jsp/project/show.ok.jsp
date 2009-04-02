<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Project" bodyClass="project" css="/css/project.css">

<div id="project">
    <p>Name: ${project.name}</p>
    <p>Description: ${project.description}</p>
</div>

<c:if test="${not empty project.colaborators}">
	<div id="colaborators">
		<h2>Colaborators:</h2>
		<ul>
			<c:forEach items="${project.colaborators}" var="colaborator">
				<li>${colaborator.name }</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<c:if test="${not empty project.iterations}">
  <div id="iterations">
    <h2>Iterations:</h2>
    <ul>
      <c:forEach items="${project.iterations}" var="iteration">
        <li>${iteration.goal }</li>
      </c:forEach>
    </ul>
  </div>
</c:if>
<c:if test="${not empty stories}">
	<div id="stories">
		<h2>Stories:</h2>
		<ul>
			<c:forEach items="${stories}" var="story">
				<li><a href="javascript:toggle('story_edit_${story.id }');">${story.name }</a> - ${story.description }<br />
					<div id="story_edit_${story.id }" style="display: none;">
						<form name="editStory" action="<c:url value='/story/update/' />" method="post">
							<input type="hidden" name="story.id" value="${story.id }"/>
							<input type="hidden" name="project.id" value="${project.id }"/>
							<p>Name: <input type="text" name="story.name" value="${story.name }"/></p>
							<p>Description: <textarea name="story.description" >${story.description }</textarea></p>
							<p> <input class="buttons" type="submit" value="Update"/>
								<input class="buttons" type="button" value="Cancel" onclick="toggle('story_edit_${story.id }');"/></p>
						</form>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<a href="javascript:toggle('story')">Add Story</a>

<div id="story" style="display: none;">
	<form name="addStory" action="<c:url value="/story/save/"/>" method="post">
		<input type="hidden" name="project.id" value="${project.id }" />
	  <p>Name: <input type="text" name="story.name"/></p>
	  <p>Description: <textarea name="story.description"></textarea></p>
	  <p><input class="buttons" type="submit" value="Create"/>
	  	<input class="buttons" type="button" value="Cancel" onclick="toggle('story');"/></p>
	</form>
</div>
<a href="javascript:toggle('colaborator')">Add Colaborator</a>
<div id="colaborator" style="display: none;">
	<form name="addColaborator" action="<c:url value="/add/colaborator/"/>" method="post">
		<input type="hidden" name="project.id" value="${project.id }" />
		<select name="colaborator.login">
			<c:forEach items="${users}" var="user">
				<option value="${user.login}">${user.name}</option>
			</c:forEach>		
		</select>
		<input type="submit" value="Add"/>
		<input class="buttons" type="button" value="Cancel" onclick="toggle('colaborator');"/>
	</form>
</div>

<a href="javascript:toggle('iteration')">Add Iteration</a>
<div id="iteration" style="display: none;">
  <form name="addIteration" action="<c:url value="/iteration/save/"/>" method="post">
    <input type="hidden" name="project.id" value="${project.id }" />
    <p>Goal: <input type="text" name="iteration.goal"/></p>
	<p>Start Date: <input type="text" name="iteration.startDate"/></p>
    <p>End Date: <input type="text" name="iteration.endDate"/></p>
	<p><input type="submit" value="Add"/>
	   <input class="buttons" type="button" value="Cancel" onclick="toggle('iteration');"/></p>
  </form>
</div>
<a href="<c:url value="/"/>">Back</a>

</calopsita:page>