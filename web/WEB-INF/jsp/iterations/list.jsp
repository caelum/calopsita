<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>

<page:applyDecorator name="iteration">
<html>
<head>
	<title><fmt:message key="project"/></title>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery.validate.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/iteration-list.js"/>"></script>
</head>

<body>

<div id="tab2">
  <div id="page-tabs">

    <div id="iterations">
	  <c:if test="${not empty project.iterations}">
	      <ul class="iterations">
	        <c:forEach items="${project.iterations}" var="iteration">
	          <li id="${iteration.current ? 'current' : ''}" class="iteration">
	  				<a class="goal" title="<fmt:message key="open" />" 
	  					href="<c:url value="/projects/${project.id }/iterations/${iteration.id}/"/>">${iteration.goal}</a>
	  				<div class="action">
			  			 <c:if test="${iteration.startable }">
			  				<a name="start ${iteration.goal }"
			  					class="ui-icon  ui-icon-play"
			  					title="<fmt:message key="start" />"
			  					href="<c:url value="/projects/${project.id }/iterations/${iteration.id }/start/"/>"></a>
			  			 </c:if>
			             <c:if test="${iteration.current}">
			                <a name="end ${iteration.goal}" 
			  					class="ui-icon  ui-icon-stop"
			  					title="<fmt:message key="end" />"
			                	href="<c:url value="/projects/${project.id }/iterations/${iteration.id}/end/"/>">
			                </a> 
			             </c:if>
			             <a class="ui-icon ui-icon-closethick" name="delete ${iteration.goal }" href="javascript:void(0)" 
							  title="<fmt:message key="delete"/>"
			                  onclick="confirmIterationDeletion('<c:url value="/projects/${project.id }/iterations/${iteration.id}/"/>')"></a>
			  		</div>
			  	<div class="description">
	  			<c:if test="${not empty iteration.endDate }" >
	  				(<fmt:message key="dueDate"/> <joda:format value="${iteration.endDate}" pattern="${format.jodaFormat}"/>)
	  			</c:if>
			  	</div>
	  		</li>
	        </c:forEach>
	      </ul>
	  </c:if>
    </div>
  </div>
</div>
</body>
</html>
</page:applyDecorator>