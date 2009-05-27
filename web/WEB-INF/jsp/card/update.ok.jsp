<%@include file="../javascripts.jspf" %>
<script type="text/javascript" src="<c:url value="/javascript/card-update.js"/>"></script>
<h2><fmt:message key="cards"/>:</h2>

<dl>
	<c:forEach items="${cards}" var="card" varStatus="s">
		<c:if test="${card.priority ne 0}">
			<%@include file="storyLine.jsp" %>
		</c:if>
	</c:forEach>
	<c:forEach items="${cards}" var="card" varStatus="s">
		<c:if test="${card.priority eq 0}">
			<%@include file="storyLine.jsp" %>
		</c:if>
	</c:forEach>
</dl>
<a href="<c:url value="/project/${project.id }/prioritization/"/>"><fmt:message key="prioritize"/></a>