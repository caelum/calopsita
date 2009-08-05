<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ attribute name="uri" description="Destination URI" required="true" type="java.lang.String" %>
<%@ attribute name="message" description="Message name" required="true" type="java.lang.String" %>
<%@ attribute name="selected" description="Is this the current tab" required="false" type="java.lang.Boolean" %>
	<li ${selected ? 'class="selected"' : '' }><a href="<c:url value="${uri}"/>"><fmt:message key="${message }"/></a></li>
