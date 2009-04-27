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
    
    <%@include file="../jsp/javascripts.jspf" %>
    
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/index.css"/>" />
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smoothness/ui.all.css"/>" />
    
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
          equalTo: "<fmt:message key="validation.equalTo" />",
          date: "<fmt:message key="validation.date" />"
      });
      function toggle(id) {
   		$("#" + id).slideToggle("normal");
   	  }
    </script>
</head>
<body class="${bodyClass}">

<div id="main">
	<div id="header">
	   <div id="logo">
         <a href="<c:url value="/"/>"><img src="<c:url value="/images/logo.png"/>" alt="calopsita" /></a>
       </div>
	   <div id="user">
        <c:if test="${not empty currentUser}">
          <div class="name">${currentUser.login}</div> 
          <a href="<c:url value="/user/logout/"/>">Logout</a>
        </c:if>
      </div>
      <div id="errors">
      	<c:if test="${not empty errors}">
      		<c:forEach var="error" items="${errors.iterator}">
				<fmt:message key="${error.key}" /><br />
			</c:forEach>
      	</c:if>
      </div>
	</div>
	
	<hr class="separador"/>
	
	<jsp:doBody />
</div>

</body>
</html>