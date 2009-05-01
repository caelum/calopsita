<li id="stories_${s.count}">
	<div>
		<a href="javascript:toggle('story_edit_${story.id }');">${story.name }</a> - ${story.description }<br />
		<div id="story_edit_${story.id }" style="display: none;">
			<form name="editStory" action="<c:url value='/story/update/' />" method="post">
				<input type="hidden" name="story.id" value="${story.id }"/>
				<input type="hidden" name="project.id" value="${project.id }"/>
				<p>
					<fmt:message key="story.name"/>
					<em>*</em><input type="text" name="story.name" value="${story.name }"/>
				</p>
				<p>
					<fmt:message key="story.description"/>
					<em>*</em><textarea name="story.description" >${story.description }</textarea>
				</p>
				<p>
					<input class="buttons" type="submit" value="<fmt:message key="update"/>"/>
					<input class="buttons" type="button" value="<fmt:message key="cancel"/>" onclick="toggle('story_edit_${story.id }');"/>
				</p>
			</form>
		</div>
	</div>
</li>
