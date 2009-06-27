<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<script type="text/javascript" src="<c:url value="/javascript/iteration-edit.js"/>"></script>
<script type="text/javascript">
	initialize('<fmt:message key="validation.dateRange"/>', '<c:url value="/images/calendar.gif"/>');
</script>
<c:set var="operation">
	<c:if test="${not empty iteration.id}">${iteration.id }/</c:if>
</c:set>
<c:set var="label">
	<c:if test="${empty iteration.id}"><fmt:message key="add"/></c:if>
	<c:if test="${not empty iteration.id}"><fmt:message key="edit"/></c:if>
</c:set>
<form id="iteration" class="hidden" name="addIteration" action="<c:url value="/projects/${project.id }/iterations/${operation }"/>" method="post">
   	<p>
		<label><fmt:message key="iteration.goal"/></label>
		<em>*</em><input type="text" name="iteration.goal" value="${iteration.goal }"/>
	</p>
	<p>
		<label><fmt:message key="iteration.startDate"/></label>
		<em></em><input type="text" name="iteration.startDate" value="${iteration.formattedStartDate }" class="datepicker"/>
	</p>
	<p>
		<label><fmt:message key="iteration.endDate"/></label>
		<em></em><input type="text" name="iteration.endDate" value="${iteration.formattedEndDate }" class="datepicker"/>
	</p>
	<p>
		<input type="submit" value="${label }"/>
	 	<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="toggle('iteration'); $('.datepicker').change();"/>
	</p>
</form>
