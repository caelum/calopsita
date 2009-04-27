<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Project" bodyClass="project" javascript="/javascript/project.js" css="/css/project.css">

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
        <li><a href="<c:url value="/iteration/${iteration.id}/show/"/>">${iteration.goal}</a></li>
      </c:forEach>
    </ul>
  </div>
</c:if>
<script type="text/javascript">
	$(function() {
		$('form[name="addStory"]').submit(function() {
			
			$(this).ajaxSubmit({
				target : '#stories',
				success : function () {
					toggle('story');
					$('#story').clearForm();
				}
			});
			return false;
		});
	});
</script>
<div id="stories">
	<c:if test="${not empty stories}">
		<%@include file="../story/update.ok.jsp" %>
	</c:if>
</div>
<a href="javascript:toggle('story')">Add Story</a><br/>

<div id="story" style="display: none;">
	<form id="addStory" name="addStory" action="<c:url value="/story/save/"/>" method="post">
		<input type="hidden" name="project.id" value="${project.id }" />
	  <p>Name: <input type="text" name="story.name"/></p>
	  <p>Description: <textarea name="story.description"></textarea></p>
	  <p><input class="buttons" type="submit" value="Create"/>
	  	<input class="buttons" type="button" value="Cancel" onclick="toggle('story');"/></p>
	</form>
</div>
<a href="javascript:toggle('colaborator')">Add Colaborator</a><br/>
<div id="colaborator" style="display: none;">
	<form id="addColaborator" name="addColaborator" action="<c:url value="/add/colaborator/"/>" method="post">
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

<a href="javascript:toggle('iteration')">Add Iteration</a><br/>
<div id="iteration" style="display: none;">
  <form id="addIteration" name="addIteration" action="<c:url value="/iteration/save/"/>" method="post">
    <input type="hidden" name="project.id" value="${project.id }" />
    <p>Goal: <input type="text" name="iteration.goal"/></p>
	<p>Start Date: <input type="text" name="iteration.startDate" class="datepicker"/></p>
    <p>End Date: <input type="text" name="iteration.endDate" class="datepicker"/></p>
	<p><input type="submit" value="Add"/>
	   <input class="buttons" type="button" value="Cancel" onclick="toggle('iteration');"/></p>
  </form>
</div>
<a href="<c:url value="/"/>">Back</a>

</calopsita:page>