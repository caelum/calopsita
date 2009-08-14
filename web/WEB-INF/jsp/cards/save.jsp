<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="recent-cards" class="information">
	<h4><fmt:message key="recently.added.cards"/></h4>
	<ul class="stories">
		<c:forEach items="${project.lastAddedCards}" var="card" varStatus="s">
			<li class="story" id="cards_${s.count}">
				<span class="name">${card.name }</span>
				<span class="action">
					<a class="ui-icon ui-icon-pencil" name="edit ${card.name }" 
						title="<fmt:message key="edit"/>"
						href="<c:url value="/projects/${card.project.id}/cards/${card.id}/"/>"></a>
					<a class="ui-icon ui-icon-closethick" name="delete ${card.name }" href="javascript:void(0)" 
						title="<fmt:message key="delete"/>"
						onclick="confirmCardDeletion('<c:url value="/projects/${card.project.id}/cards/${card.id}/"/>', ${not empty card.subcards })"></a>
				</span>
			</li>
		</c:forEach>
	</ul>
</div>