<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>
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
<div id="timeline">
  <div id="start_and_today">
    <calopsita:timebox date="${iteration.startDate}" prefix="start" />
  
    <hr class="line" id="start_today_line" />
    
    <calopsita:timebox date="${today}" prefix="today" sufix="_start"/>
  </div>
    
  <hr class="line" id="start_end_line" />
    
  <div id="today_and_end">
	<calopsita:timebox date="${today}" prefix="today" sufix="_end"/>
	
    <hr class="line" id="today_end_line" />
          
	<calopsita:timebox date="${iteration.endDate}" prefix="end" />
  </div>
  
  
</div>
<div id="iteration_text">
	<p><fmt:message key="iteration.goal"/>: ${iteration.goal}</p>
</div>