<%@include file="../javascripts.jspf" %>
<script type="text/javascript">
	$(function() {
		function bind() {
			$('form[name="editStory"]').ajaxForm({
				beforeSubmit: function () {
					$('[id*="story_edit"]:visible').slideToggle("normal");
				},
				success: function(data) {
					$('#stories').html(data);
					bind();
				}
			});
		}
		bind();
	});
</script>
<h2>Stories:</h2>

<ol>
	<c:forEach items="${stories}" var="story" varStatus="s">
		<li id="stories_${s.count}">
			<div>
				<a href="javascript:toggle('story_edit_${story.id }');">${story.name }</a> - ${story.description }<br />
				<div id="story_edit_${story.id }" style="display: none;">
					<form name="editStory" action="<c:url value='/story/update/' />" method="post">
						<input type="hidden" name="story.id" value="${story.id }"/>
						<input type="hidden" name="project.id" value="${project.id }"/>
						<p>Name: <input type="text" name="story.name" value="${story.name }"/></p>
						<p>Description: <textarea name="story.description" >${story.description }</textarea></p>
						<p> <input class="buttons" type="submit" value="Update"/>
							<input class="buttons" type="button" value="Cancel" onclick="toggle('story_edit_${story.id }');"/></p>
					</form>
				</div>
			</div>
		</li>
	</c:forEach>
</ol>
<a href="<c:url value="/project/${project.id }/prioritize/"/>">Prioritize</a>