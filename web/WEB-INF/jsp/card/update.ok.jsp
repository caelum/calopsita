<%@include file="../javascripts.jspf" %>
<script type="text/javascript" src="<c:url value="/javascript/card-update.js"/>"></script>
<h2><fmt:message key="cards"/>:</h2>

<dl>
	<c:forEach items="${cards}" var="card" varStatus="s">
		<%@include file="storyLine.jsp" %>
	</c:forEach>
</dl>
<a href="<c:url value="/project/${project.id }/prioritization/"/>"><fmt:message key="prioritize"/></a>