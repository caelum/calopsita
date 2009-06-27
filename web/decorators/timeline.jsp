<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>
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