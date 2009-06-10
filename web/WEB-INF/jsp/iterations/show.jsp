<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
  <title><fmt:message key="iteration"/></title>
  
  <script type="text/javascript" src="<c:url value="/javascript/jquery/selectableDraggable.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/javascript/iteration-show.js"/>"></script>
  <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/iteration.css"/>" />
  <script type="text/javascript">
  $(document).ready(function() {
      var daysBetweenTodayAndStartDate = (parseInt('${today.dayOfYear}') + 365 * parseInt('${today.year}')) - (parseInt('${iteration.startDate.dayOfYear}') + 365 * parseInt('${iteration.startDate.year}'));
      var daysBetweenEndDateAndToday = (parseInt('${iteration.endDate.dayOfYear}') + 365 * parseInt('${iteration.endDate.year}')) - (parseInt('${today.dayOfYear}') + 365 * parseInt('${today.year}'));
      var daysBetweenEndDateAndStartDate = (parseInt('${iteration.endDate.dayOfYear}') + 365 * parseInt('${iteration.endDate.year}')) - (parseInt('${iteration.startDate.dayOfYear}') + 365 * parseInt('${iteration.startDate.year}'));

      timeline(daysBetweenTodayAndStartDate, daysBetweenEndDateAndToday, daysBetweenEndDateAndStartDate);
  });
    initialize('<c:url value="/projects/${iteration.project.id }/iterations/${iteration.id}/cards/"/>');
  </script>
</head>

<body>

<div id="tab2">
  <%@include file="iteration.jsp" %>
</div>
</body>
</html>