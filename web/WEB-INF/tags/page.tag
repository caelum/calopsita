<%--
	Define header e footer de uma pagina do calopsita.
	(possui alguns atributos dinamicos)
--%>
<%@ tag body-content="scriptless" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>

<%@ attribute name="title" required="true" description="titulo da pagina" %>
<%@ attribute name="bodyClass" description="classe pro body" %>
<%@ attribute name="description" description="meta description pra essa pagina" %>
<%@ attribute name="keywords" description="meta keywords pra essa pagina" %>
<%@ attribute name="javascript" description="algum arquivo javascript adicional" %>
<%@ attribute name="css" description="algum arquivo css adicional" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head profile="http://gmpg.org/xfn/11">	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="pt-br"/>
	
	<meta name="description" content="<c:out value="${description}" default="${defaultDescription}"/>" />
	<meta name="keywords" content="<c:out value="${keywords}" default="${defaultKeywords}"/>" />
  
    <title>${title} ${fn:startsWith(title, 'Calopsita - Gerenciamento de softwares para equipes ágeis')?'': '- Calopsita - Gerenciamento de softwares para equipes ágeis'}</title>
    <script type="text/javascript" src="<c:url value="/javascript/jquery-1.2.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/javascript/jquery.validate.min.js"/>"></script>
    
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/index.css"/>" />
    
	<c:forTokens items="${javascript}" delims="," var="arquivo">
		<script type="text/javascript" src="<c:url value="${arquivo}"/>"></script>
	</c:forTokens>
	<c:forTokens items="${css}" delims="," var="arquivo">
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="${arquivo}"/>" />
	</c:forTokens>
	
	<link rel="shortcut icon" href="<c:url value="/images/icon.ico"/>" type="image/x-icon" />
  
    <script language="javascript" type="text/javascript">
      $.extend($.validator.messages, {
          required: "<fmt:message key="validation.required" />",
          minlength: jQuery.format("<fmt:message key="validation.minlength" />"),
          email: "<fmt:message key="validation.email" />",
          equalTo: "<fmt:message key="validation.equalTo" />"
      });
      function toggle(id) {
   		$("#" + id).toggle();
   	  }
    </script>
</head>
<body class="${bodyClass}">

<div class="main">
	<div id="header">
	   <div class="logo">
         <a href="http://www.calopsita.com"><img src="<c:url value="/images/logo.png"/>" alt="calopsita" /></a>
       </div>
	   <div class="user">
        <c:if test="${empty currentUser}">
          <a href="<c:url value="/user/formSignUp/"/>">Sign Up</a>
          <a href="<c:url value="/user/formLogin/"/>">Login</a>
        </c:if>
        <c:if test="${not empty currentUser}">
          ${currentUser.login} 
          <ul>
          	<li><a href="<c:url value="/project/form/"/>">New Project</a></li>
          	<li><a href="<c:url value="/project/list/"/>">List Projects</a></li>
          	<li><a href="<c:url value="/user/logout/"/>">Logout</a></li>
          </ul>
        </c:if>
      </div>
      <div class="errors">
      	<c:if test="${not empty errors}">
      		<c:forEach var="error" items="${errors.iterator}">
				<fmt:message key="${error.key}" /><br />
			</c:forEach>
      	</c:if>
      </div>
	</div>
	
	<hr class="separador"/>
	
	<jsp:doBody />

	<hr class="separador"/>
	<div class="footer">

	</div>
</div>

</body>
</html>