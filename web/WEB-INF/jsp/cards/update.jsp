<%@include file="../javascripts.jspf" %>
<script type="text/javascript" src="<c:url value="/javascript/card-update.js"/>"></script>
<dl>
	<c:forEach items="${cards}" var="card" varStatus="s">
		<%@include file="storyLine.jsp" %>
	</c:forEach>
</dl>
