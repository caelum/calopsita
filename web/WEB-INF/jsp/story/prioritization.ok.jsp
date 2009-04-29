<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Project" bodyClass="project" javascript="/javascript/iteration.js" css="/css/iteration.css">
<script type="text/javascript">
	var max_priority = 0;
	function storyCard(name, description, id, count, priority) {
		var div = $('<div class="story" name="' + name + '" title="' + description.substring(0,40) + '"><p>' + name + '</p></div>');
		div.append('<input type="hidden" name="stories[' + count + '].id" value="' + id + '" />');
		div.append('<input class="priority" type="hidden" name="stories[' + count + '].priority" value="' + priority + '" />');
		div.dblclick(function() {
			showDialog(name, description);
		});
		return div;
	}

	function getOrCreateDiv(priority) {
		if (priority > max_priority + 1) getOrCreateDiv(priority - 1);
		if (priority > max_priority) max_priority = priority;
		
		var div = $('#level_' + priority);
		if (div.length == 0) {
			$('#board').append('<div class="title">Priority ' + priority + '</div>');
			div = $('<div id="level_' + priority + '" class="board" title="Priority ' + priority + '" priority="' + priority + '"></div>');
			div.hide();
			$('#board').append(div);
			div.show('highlight');
		}
		return div;
	}
	function moveSelectedTo(div) {
		$('.ui-selected').not('.clone').each(function() {
			$(this).children('.priority').val(div.attr('priority'));
			$(this).removeClass('ui-selected');
			div.append(this);
		});
	}
	$(function() {
		<c:forEach items="${stories}" var="s" varStatus="status">
			<c:set var="newline" value="
"/>
			getOrCreateDiv(${s.priority}).append(storyCard('${s.name}', '${fn:replace(s.description, newline, "<br/>")}', 
					${s.id}, ${status.count} - 1, ${s.priority}));
		</c:forEach>

		function bind() {
			$('.board').not('#lowerPriority').droppable({
				accept: function (element) {
					return $(element).is('.story') && $(element).parents('#' + this.id).length == 0;
				},
				tolerance: 'pointer',
				drop: function(event, ui) {
					moveSelectedTo($(this));
				}
			});
		}
		bind();
		$('#prioritizationForm').selectable({filter: '.story'});
		$('.story').selectableAndDraggable();
		
		$('#lowerPriority').droppable({
			accept: '.story',
			tolerance: 'pointer',
			drop: function(event, ui) {
				var div = getOrCreateDiv(max_priority + 1);
				moveSelectedTo(div);
				bind();
			}
		});
		function changeWidth() {
			var w = $('body').width();
			$('.board').css({width : w * 0.55});
			$('.table').css({width : w * 0.45 - 50});
		}
		changeWidth();
		$(window).resize(changeWidth);
	});

</script>
<div id="project">
    <p>Name: ${project.name}</p>
    <p>Description: ${project.description}</p>
</div>
<form id="prioritizationForm" action="<c:url value="/story/prioritize/"/>" method="post">
	<input type="submit" value="Save Priorization" />
	<input type="hidden" name="project.id" value="${project.id }" />
	<div class="table">
		<div class="title">Infinity Priority</div>
		<div id="level_0" class="board" title="Infinity Priority" priority="0"></div>
	</div>
	<div id="board">
		
	</div>
	<div class="title">New Priority Level</div>
	<div id="lowerPriority" class="board" title="New Priority Level" priority="200"></div>
</form>
<a href="<c:url value="/project/${project.id }/show/"/>">Back</a>
</calopsita:page>