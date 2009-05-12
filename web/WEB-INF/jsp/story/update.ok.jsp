<%@include file="../javascripts.jspf" %>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/javascript/story-update.js"/>"></script>
<h2><fmt:message key="stories"/>:</h2>

<dl>
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
</dl>
<a href="<c:url value="/project/${project.id }/prioritization/"/>"><fmt:message key="prioritize"/></a>