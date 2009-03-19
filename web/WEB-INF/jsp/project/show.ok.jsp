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
<a href="javascript:toggle('form')">Add Colaborator</a>
<div id="form" style="display: none;">
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
<a href="<c:url value="/"/>">Voltar</a>

</calopsita:page>