<%@include file="../javascripts.jspf" %>
<script type="text/javascript" src="<c:url value="/javascript/story-update.js"/>"></script>
<h2><fmt:message key="stories"/>:</h2>

<ol>
	<c:forEach items="${stories}" var="story" varStatus="s">
		<c:if test="${story.priority ne 0}">
			<%@include file="storyLine.jsp" %>
		</c:if>
	</c:forEach>
	<c:forEach items="${stories}" var="story" varStatus="s">
		<c:if test="${story.priority eq 0}">
			<%@include file="storyLine.jsp" %>
		</c:if>
	</c:forEach>
</ol>
<a href="<c:url value="/project/${project.id }/prioritization/"/>"><fmt:message key="prioritize"/></a>