<%@include file="../javascripts.jspf" %>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/javascript/card-update.js"/>"></script>
<ul class="stories">
	<c:forEach items="${cards}" var="card" varStatus="s">
		<%@include file="storyLine.jsp" %>
	</c:forEach>
</ul>
