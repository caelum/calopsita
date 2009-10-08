<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="calopsita" tagdir="/WEB-INF/tags" %>

<div id="recent-cards" class="information">
	<h4><fmt:message key="recently.added.cards"/></h4>
	<calopsita:cards cards="${project.lastAddedCards}" />
</div>