<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Project" bodyClass="project" javascript="/javascript/iteration.js" css="/css/iteration.css">
<script type="text/javascript">
	var max_priority = 0;
	function storyCard(name, id, count, priority) {
		var div = $('<div class="story">' + name + '</div>');
		div.append('<input type="hidden" name="stories[' + count + '].id" value="' + id + '" />');
		div.append('<input class="priority" type="hidden" name="stories[' + count + '].priority" value="' + priority + '" />');
		return div;
	}

	function getOrCreateDiv(priority) {
		if (priority > max_priority + 1) getOrCreateDiv(priority - 1);
		if (priority == max_priority + 1) max_priority = priority;
		
		var div = $('#level_' + priority);
		if (div.length == 0) {
			$('#board').append('<div class="title" style="font-weight: bold; font-size: 10px; color: blue;">Priority ' + priority + '</div>');
			div = $('<div id="level_' + priority + '" class="board" title="Priority ' + priority + '" priority="' + priority + '"></div>');
			div.hide();
			$('#board').append(div);
			div.show('highlight');
		}
		return div;
	}
	$(function() {
		<c:forEach items="${stories}" var="s" varStatus="status">
			getOrCreateDiv(${s.priority}).append(storyCard('${s.name}', ${s.id}, ${status.count} - 1, ${s.priority}));
		</c:forEach>

		function moveSelectedTo(div) {
			$('.ui-selected').not('.clone').each(function() {
				$(this).children('.priority').val(div.attr('priority'));
				$(this).removeClass('ui-selected');
				div.append(this);
			});
		}
		function bind() {
			$('.board').not('#lowerPriority').droppable({
				accept: '.story',
				drop: function(event, ui) {
					moveSelectedTo($(this));
				}
			});
		}
		bind();
		$('.board').selectable();
		$('.story').selectableAndDraggable();
		
		$('#lowerPriority').droppable({
			accept: '.story',
			drop: function(event, ui) {
				var div = getOrCreateDiv(max_priority + 1);
				moveSelectedTo(div);
				bind();
			}
		});
	});

</script>
<div id="project">
    <p>Name: ${project.name}</p>
    <p>Description: ${project.description}</p>
</div>
<form name="priorizationForm" action="<c:url value="/story/prioritize/"/>" method="post">
	<input type="submit" value="Save Priorization" />
	<input type="hidden" name="project.id" value="${project.id }" />
	<div id="board">
		
	</div>
	<div class="title" style="font-weight: bold; font-size: 10px; color: blue;">Lower Priority</div>
	<div id="lowerPriority" class="board" title="Lower Priority" priority="200"></div>
	
</form>
<a href="<c:url value="/project/show/${iteration.project.id }/"/>">Back</a>
</calopsita:page>