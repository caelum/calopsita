<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/css/impromptu.css"/>" />
<script type="text/javascript" src="<c:url value="/javascript/jquery/jquery-impromptu.2.5.min.js"/>"></script>

<dt id="stories_${s.count}">
	<a href="<c:url value="/story/${story.id }/edit/"/>">${story.name }</a>
	<a class="delete" name="delete ${story.name }" href="javascript:void(0)"
		onclick="confirmStoryDeletion('<c:url value="/story/${story.id }/delete/"/>', ${not empty story.substories })">X</a>
</dt>
<dd><pre>${fn:escapeXml(story.description) }</pre></dd>
