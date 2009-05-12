<dt id="stories_${s.count}">
	<a href="<c:url value="/story/${story.id }/edit/"/>">${story.name }</a><br />
</dt>
<dd><pre>${fn:escapeXml(story.description) }</pre></dd>
