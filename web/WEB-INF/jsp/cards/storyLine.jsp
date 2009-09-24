<li class="story" id="cards_${s.count}">
	<span class="name" onclick="toggleDescription(this.parentNode);">${card.name } <sub class="creator">by ${card.creator.login }</sub></span>
	<span class="action">
		<a class="ui-icon ui-icon-pencil" name="edit ${card.name }" 
			title="<fmt:message key="edit"/>"
			href="<c:url value="/projects/${card.project.id}/cards/${card.id}/"/>"></a>
		<a class="ui-icon ui-icon-closethick" name="delete ${card.name }" href="javascript:void(0)" 
			title="<fmt:message key="delete"/>"
			onclick="confirmCardDeletion('<c:url value="/projects/${card.project.id}/cards/${card.id}/"/>', ${not empty card.subcards })"></a>
	</span>
	<div class="description"><pre>${fn:escapeXml(card.description) }</pre></div>
</li>
