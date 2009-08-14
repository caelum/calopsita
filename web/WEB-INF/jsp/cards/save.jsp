<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${project.lastAddedCards}" var="card" varStatus="s">
	<%@include file="storyLine.jsp" %>
</c:forEach>