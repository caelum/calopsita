<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<script type="text/javascript" src="<c:url value="/javascript/iteration-edit.js"/>"></script>
<script type="text/javascript">
	initialize('<fmt:message key="validation.dateRange"/>');
</script>
<c:set var="operation">
	<c:if test="${empty iteration.id}">save</c:if>
	<c:if test="${not empty iteration.id}">${iteration.id }/update</c:if>
</c:set>
<form id="iteration" class="hidden" name="addIteration" action="<c:url value="/iteration/${operation }/"/>" method="post">
  	<input type="hidden" name="iteration.project.id" value="${project.id }" />
   	<p>
		<label><fmt:message key="iteration.goal"/></label>
		<em>*</em><input type="text" name="iteration.goal" value="${iteration.goal }"/>
	</p>
	<p>
		<label><fmt:message key="iteration.startDate"/></label>
		<em></em><input type="text" name="iteration.startDate" value="${iteration.startDate }" class="datepicker"/>
	</p>
	<p>
		<label><fmt:message key="iteration.endDate"/></label>
		<em></em><input type="text" name="iteration.endDate" value="${iteration.endDate }" class="datepicker"/>
	</p>
	<p>
		<input type="submit" value="<fmt:message key="add"/>"/>
	 	<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="toggle('iteration');"/>
	</p>
</form>
