<li class="story" id="${storyId}_${s.count}" name="${story.name }" ondblclick="showDialog('${story.name}', 'description_${story.id }')">
	<p>${story.name }</p>
	<pre class="hidden" id="description_${story.id }">${fn:escapeXml(story.description) }</pre>
	<span class="hidden">${story.id }</span>
</li>
