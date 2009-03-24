<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Project" bodyClass="project">

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
<c:if test="${not empty stories}">
	<div id="stories">
		<h2>Stories:</h2>
		<ul>
			<c:forEach items="${stories}" var="story">
				<li><a href="javascript:toggle('story_edit_${story.id }');">${story.name }</a> - ${story.description }<br />
					<div id="story_edit_${story.id }">
						<form name="editStory" action="<c:url value='/story/update/' />" method="post">
							<input type="hidden" name="story.id" value="${story.id }"/>
							<input type="hidden" name="project.id" value="${project.id }"/>
							<p>Name: <input type="text" name="story.name" /></p>
							<p>Description: <input type="text" name="story.description" /></p>
							<p><input class="buttons" type="submit" value="Update"/></p>
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
	  <p>Description: <input type="text" name="story.description"/></p>
	  <p><input class="buttons" type="submit" value="Create"/></p>
	</form>
</div>
<a href="javascript:toggle('colaborator')">Add Colaborator</a>
<div id="colaborator" style="display: none;">
	<form name="addColaborator" action="<c:url value="/add/colaborator/"/>">
		<input type="hidden" name="project.id" value="${project.id }" />
		<select name="colaborator.login">
			<c:forEach items="${users}" var="user">
				<option value="${user.login}">${user.name}</option>
			</c:forEach>		
		</select>
		<input type="submit" value="Add"/>
	</form>
</div>
<a href="<c:url value="/"/>">Back</a>

</calopsita:page>