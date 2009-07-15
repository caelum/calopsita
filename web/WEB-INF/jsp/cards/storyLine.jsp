<dt class="story" id="cards_${s.count}">
	<a href="<c:url value="/projects/${card.project.id}/cards/${card.id}/"/>">${card.name }</a>
	<a class="action ui-icon ui-icon-closethick" name="delete ${card.name }" href="javascript:void(0)" 
		title="<fmt:message key="delete"/>"
		onclick="confirmCardDeletion('<c:url value="/projects/${card.project.id}/cards/${card.id}/"/>', ${not empty card.subcards })"></a>
	<a class="action ui-icon ui-icon-pencil" name="edit ${card.name }" 
		title="<fmt:message key="edit"/>"
		href="<c:url value="/projects/${card.project.id}/cards/${card.id}/"/>'"></a>
</dt>
<dd><pre>${fn:escapeXml(card.description) }</pre></dd>
