<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>	
		<title><fmt:message key="iteration"/></title>
		<decorator:head />
		<c:if test="${not empty iteration}">
			<script type="text/javascript" src="<c:url value="/javascript/jquery/selectableDraggable.js"/>"></script>
			<script type="text/javascript">
				$(document).ready(function() {
					var daysBetweenTodayAndStartDate = (parseInt('${today.dayOfYear}') + 365 * parseInt('${today.year}')) - (parseInt('${iteration.startDate.dayOfYear}') + 365 * parseInt('${iteration.startDate.year}'));
					var daysBetweenEndDateAndToday = (parseInt('${iteration.endDate.dayOfYear}') + 365 * parseInt('${iteration.endDate.year}')) - (parseInt('${today.dayOfYear}') + 365 * parseInt('${today.year}'));
					var daysBetweenEndDateAndStartDate = (parseInt('${iteration.endDate.dayOfYear}') + 365 * parseInt('${iteration.endDate.year}')) - (parseInt('${iteration.startDate.dayOfYear}') + 365 * parseInt('${iteration.startDate.year}'));
			
					timeline(daysBetweenTodayAndStartDate, daysBetweenEndDateAndToday, daysBetweenEndDateAndStartDate);
					$('#timeline').insertAfter('#page-tabs ul:first');
				});
				initialize('<c:url value="/projects/${iteration.project.id }/iterations/${iteration.id}/cards/"/>');
			</script>
  		</c:if>
	</head>

	<body>

		<calopsita:sub-menu>
			<calopsita:sub-menu-item uri="/projects/${project.id}/iterations/current/" message="iteration.current" />
			<calopsita:sub-menu-item uri="/projects/${project.id}/iterations/${iteration.id}/edit/" message="edit" />
		</calopsita:sub-menu>
		<c:if test="${not empty iteration}">
			<%@include file="timeline.jsp" %>
	
			<div id="iteration_text">
				<p><fmt:message key="iteration.goal"/>: ${iteration.goal}</p>
			</div>
		</c:if>
		<decorator:body />
	
	</body>
</html>