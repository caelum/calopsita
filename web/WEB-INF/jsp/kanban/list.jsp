<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>

<page:applyDecorator name="admin">

	<ul id="columns" class="pretty">
		<c:forEach items="${kanbanColumnList}" var="type">
			<li>${type.name }</li>
		</c:forEach>
	</ul>

	<div class="clear"><a href="javascript:toggle('formKanban')"><fmt:message
		key="add.kanbanColumn" /></a>
	<form class="hidden" id="formKanban"
		action="<c:url value="/projects/${project.id }/kanban_configuration" />"
		method="post">
	<p><label><fmt:message key="column.name" /></label> <em>*</em><input
		type="text" name="column.name" /></p>
	</fieldset>
	<p><input class="buttons" type="submit"
		value="<fmt:message key="add"/>" /> <input class="buttons"
		type="reset" value="<fmt:message key="cancel"/>"
		onclick="toggleForm();" /></p>
	</form>
	</div>
</page:applyDecorator>