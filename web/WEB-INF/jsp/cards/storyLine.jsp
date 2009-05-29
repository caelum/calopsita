<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>

<dt id="cards_${s.count}">
	<a href="<c:url value="/cards/${card.id }/edit/"/>">${card.name }</a>
	<a class="delete" name="delete ${card.name }" href="javascript:void(0)"
		onclick="confirmCardDeletion('<c:url value="/cards/${card.id }/delete/"/>', ${not empty card.subcards })">X</a>
</dt>
<dd><pre>${fn:escapeXml(card.description) }</pre></dd>
