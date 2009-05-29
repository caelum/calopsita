<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title><fmt:message key="project"/></title>
</head>

<body>

<div id="tab2">
  <ul id="tabnav">
    <li class="tab1"><a href="<c:url value="/iterations/${project.id }/current/"/>">Current iteration</a></li>
    <li class="tab2"><a href="<c:url value="/iterations/${project.id }/list/"/>">Iterations</a></li>
    <li class="tab3"><a href="<c:url value="/project/${project.id }/cards/"/>">Cards</a></li>
    <li class="tab4"><a href="<c:url value="/project/${project.id }/admin/"/>">Admin</a></li>
  </ul>
  
  <div id="projects">
      <p><fmt:message key="project.name"/>: ${project.name}</p>
  </div>
    
  <c:if test="${not empty project.iterations}">
    <div id="iterations">
      <h2><fmt:message key="iterations"/>:</h2>
      <ul>
        <c:forEach items="${project.iterations}" var="iteration">
          <li id="${iteration.current ? 'current' : ''}">
  			<a href="<c:url value="/iteration/${iteration.id}/show/"/>">${iteration.goal}</a>
  			<c:if test="${not empty iteration.endDate }" >
  				(<fmt:message key="dueDate"/> ${iteration.endDate })
  			</c:if>
  			<c:if test="${iteration.startable }">
  				<a name="start ${iteration.goal }" href="<c:url value="/iteration/${iteration.id }/start/"/>"><fmt:message key="start" /></a>
  			</c:if>
              <c:if test="${iteration.current}">
                <a name="end ${iteration.goal}" href="<c:url value="/iteration/${iteration.id}/end/"/>">
                  <fmt:message key="end"/>
                </a> 
              </c:if>
              <a class="delete" name="delete ${iteration.goal }" href="javascript:void(0)"
                  onclick="confirmIterationDeletion('<c:url value="/iteration/${iteration.id}/delete/"/>')">X</a>
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