<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Iteration" bodyClass="iteration" javascript="/WEB-INF/javascript/iteration.js" css="/css/iteration.css">

<div id="iteration">
    <p><fmt:message key="iteration.goal"/>: ${iteration.goal}</p>
    <c:if test="${not empty iteration.startDate}">
	    <p><fmt:message key="iteration.startDate"/>: ${iteration.startDate}</p>
    </c:if>
    <c:if test="${not empty iteration.endDate}">
	    <p><fmt:message key="iteration.endDate"/>: ${iteration.endDate}</p>
    </c:if>
</div>

<script type="text/javascript">
	function prepare() {
		$('.selectable').selectable({
			filter:'li'
		});

		$(".selectable li").selectableAndDraggable();
		

		$('#todo_stories').droppable({
			accept: '.story',
			tolerance: 'pointer',
			drop: todo_stories
		});
		$('#done_stories').droppable({
			accept: '.story',
			tolerance: 'pointer',
			drop: done_stories
		});
		$('#backlog').droppable({
			accept: '.story',
			tolerance: 'pointer',
			drop: remove_stories
		});
		$('.dialog').dialog({
			autoOpen: false,
			bgiframe: true,
			modal: true,
			width: '500px',
			show: 'highlight',
			hide: 'highlight'
		});
	};
	$(prepare);
	function get_params(div, status) {
		var params = {};
		$(div + ' .ui-selected').not('.clone').each(function(c, e) {
			params['stories[' + c + '].id'] = $('.hidden', e).text();
			params['stories[' + c + '].status'] = status;
		});
		params['iteration.id']=${iteration.id};
		return params;
	}
	function modifyStories(div, status, logic) {
		var params = get_params(div, status);

		$.ajax({
			url: logic,
			data: params,
			success: function(data) {
				$('#todo_stories ol').html($('#todo_stories ol', data).html());
				$('#done_stories ol').html($('#done_stories ol', data).html());
				$('#backlog ol').html($('#backlog ol', data).html());
				prepare();
			}
		});
	}
	function todo_stories() {
		modifyStories('.selectable', 'TODO', '<c:url value="/iteration/updateStories/"/>');	
	}
	function done_stories() {
		modifyStories('.selectable', 'DONE', '<c:url value="/iteration/updateStories/"/>');	
	}
	function remove_stories() {
		modifyStories('.stories', 'TODO', '<c:url value="/iteration/removeStories/"/>');
	}
	function show_help() {
		$('#help').dialog('open');
		return false;
	}
	function open_dialog(id) {
		$('#dialog_' + id).dialog('open');
	}
</script>
<div id="help" class="dialog" title="Adding and Removing Stories">
	Drag and drop stories from backlog to Stories to add the stories to iteration. <br/>
	Drag and drop stories from Stories to Backlog to remove the stories from the iteration. <br/>
	You can select more than one story at time.
</div>
<div id="todo_stories" class="selectable stories">
	<h2>To do <a href="#" onclick="return show_help()">?</a></h2>
	<ol id="todo_list" class="board">
		<c:forEach items="${iteration.todoStories}" var="story" varStatus="s">
			<li class="story" id="stories_${s.count}" name="${story.name }" ondblclick="showDialog('${story.name}', '${story.description }')">
				<p>${story.name }</p>
				<span class="hidden">${story.id }</span>
			</li>
		</c:forEach>
	</ol>
</div>
<div id="done_stories" class="selectable stories">
	<h2>Done <a href="#" onclick="return show_help()">?</a></h2>
	<ol id="done_list" class="board">
		<c:forEach items="${iteration.doneStories}" var="story" varStatus="s">
			<li class="story" id="stories_${s.count}" name="${story.name }" ondblclick="showDialog('${story.name}', '${story.description }')">
				<p>${story.name }</p>
				<span class="hidden">${story.id }</span>
			</li>
		</c:forEach>
	</ol>
</div>
<div id="backlog" class="selectable">
	<h2>BackLog</h2>

	<ol id="backlog_list" class="board">
		<c:forEach items="${otherStories}" var="story" varStatus="s">
			<li class="story" id="backlog_${s.count}" name="${story.name }" ondblclick="showDialog('${story.name}', '${story.description }')">
				<p>${story.name }</p>
				<span class="hidden">${story.id }</span>
			</li>
		</c:forEach>
	</ol>
</div>

<a href="<c:url value="/project/${iteration.project.id }/show/"/>">Back</a>

</calopsita:page>