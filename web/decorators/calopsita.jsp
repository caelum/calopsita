<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>	
    <%@include file="../WEB-INF/jsp/style-and-js.jsp" %>

    <title><decorator:title default="Home" /> - Calopsita - Agile teams project management tool</title>
	<decorator:head />

    <%@include file="../WEB-INF/jsp/extensions.jsp" %>
    <script type="text/javascript" src="<c:url value="/javascript/help.js"/>"></script>
	<script type="text/javascript">
	  //<![CDATA[
	  	setNewbie(${currentUser.newbie});
	  	setUrl('<c:url value="/users/toggleNewbie"/>');
	  //]]>
	</script>
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
        <div id="toggleHelp" ${currentUser.newbie? 'class="newbie"' : '' } title="<fmt:message key="help.toggle" />">
       		<span>HELP: <strong>${currentUser.newbie? 'ON' : 'OFF' }</strong></span>
        </div>
       </div>
	</div>
	
    <div id="errors">
		<c:if test="${not empty errors}">
			<c:forEach var="error" items="${errors}">
				${error.category}  ${error.message}<br />
			</c:forEach>
		</c:if>
     </div>
	
	<decorator:body />
</div>

</body>
</html>