<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project"/></title>
</head>

<body>

<div id="tab2">
  
  <div id="projects">
      <p><fmt:message key="project.name"/>: ${project.name}</p>
  </div>
    
  <c:if test="${not empty project.iterations}">
    <div id="iterations">
      <h2><fmt:message key="iterations"/>:</h2>
      <ul>
        <c:forEach items="${project.iterations}" var="iteration">
          <li id="${iteration.current ? 'current' : ''}">
  			<a href="<c:url value="/projects/${project.id }/iterations/${iteration.id}/"/>">${iteration.goal}</a>
  			<c:if test="${not empty iteration.endDate }" >
  				(<fmt:message key="dueDate"/> ${iteration.endDate })
  			</c:if>
  			<c:if test="${iteration.startable }">
  				<a name="start ${iteration.goal }" href="<c:url value="/projects/${project.id }/iterations/${iteration.id }/start/"/>"><fmt:message key="start" /></a>
  			</c:if>
              <c:if test="${iteration.current}">
                <a name="end ${iteration.goal}" href="<c:url value="/projects/${project.id }/iterations/${iteration.id}/end/"/>">
                  <fmt:message key="end"/>
                </a> 
              </c:if>
              <a class="delete" name="delete ${iteration.goal }" href="javascript:void(0)"
                  onclick="confirmIterationDeletion('<c:url value="/projects/${project.id }/iterations/${iteration.id}/"/>')">X</a>
  		</li>
        </c:forEach>
      </ul>
    </div>
  </c:if>
  
  <a href="javascript:toggle('iteration'); document.addIteration.reset();"><fmt:message key="project.addIteration"/></a><br/>
  <%@include file="editForm.jsp" %>
  <a href="<c:url value="/"/>"><fmt:message key="back"/></a>
</div>
</body>
</html>