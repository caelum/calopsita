<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>	
    <%@include file="../WEB-INF/jsp/style-and-js.jsp" %>

    <title><decorator:title default="Home" /> - Calopsita - Agile teams project management tool</title>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/project.css"/>" />
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
    <script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/javascript/project-show.js"/>"></script>
	<decorator:head />
</head>

<body>

<div id="main">
	<div id="header">
	   <div id="logo">
         <a href="<c:url value="/"/>"><img src="<c:url value="/images/logo.png"/>" alt="calopsita" /></a>
       </div>
	   <div id="user">
        <c:if test="${not empty currentUser}">
          <div class="name">${currentUser.login}</div> 
          <a href="<c:url value="/users/logout/"/>">Logout</a>
        </c:if>
      </div>
	</div>

	<div id="tabs">	
		<ul id="tabnav">
			<li class="tab1"><a href="<c:url value="/projects/${project.id }/iterations/current/"/>">Current iteration</a></li>
			<li class="tab2"><a href="<c:url value="/projects/${project.id }/iterations/"/>">Iterations</a></li>
			<li class="tab3"><a href="<c:url value="/projects/${project.id }/cards/"/>">Cards</a></li>
			<li class="tab4"><a href="<c:url value="/projects/${project.id }/admin/"/>">Admin</a></li>
		</ul>
	</div>
	
    <div id="errors">
		<c:if test="${not empty errors}">
			<c:forEach var="error" items="${errors.iterator}">
				<fmt:message key="${error.key}" /><br />
			</c:forEach>
		</c:if>
     </div>
	
	<decorator:body />
</div>

</body>
</html>