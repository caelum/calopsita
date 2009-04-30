<li id="stories_${s.count}">
	<div>
		<a href="javascript:toggle('story_edit_${story.id }');">${story.name }</a> - ${story.description }<br />
		<div id="story_edit_${story.id }" style="display: none;">
			<form name="editStory" action="<c:url value='/story/update/' />" method="post">
				<input type="hidden" name="story.id" value="${story.id }"/>
				<input type="hidden" name="project.id" value="${project.id }"/>
				<p>
					<label for="story.name">Name</label>
					<em>*</em><input type="text" name="story.name" value="${story.name }"/>
				</p>
				<p>
					<label for="story.description">Description</label>
					<em>*</em><textarea name="story.description" >${story.description }</textarea>
				</p>
				<p>
					<input class="buttons" type="submit" value="Update"/>
					<input class="buttons" type="button" value="Cancel" onclick="toggle('story_edit_${story.id }');"/>
				</p>
			</form>
		</div>
	</div>
</li>
