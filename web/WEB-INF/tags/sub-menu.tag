<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ attribute name="class" description="CSS class for form" required="false" type="java.lang.String" %>


<ul id="sub-menu" class="${class }">
	<jsp:doBody />
</ul>
