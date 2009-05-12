<dt id="stories_${s.count}">
	<a href="<c:url value="/story/${story.id }/edit/"/>">${story.name }</a>
	<a class="delete" name="delete ${story.name }" href="javascript:void(0)"
		onclick="confirmDeletion('<c:url value="/story/${story.id }/delete/"/>')">X</a>
</dt>
<dd><pre>${fn:escapeXml(story.description) }</pre></dd>
