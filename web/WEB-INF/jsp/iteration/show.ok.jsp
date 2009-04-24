<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Iteration" bodyClass="iteration" javascript="/javascript/iteration.js" css="/css/iteration.css">

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
		

		$('#stories').droppable({
			accept: '#backlog .story',
			drop: add_stories
		});
		$('#backlog').droppable({
			accept: '#stories .story',
			drop: remove_stories
		});
		$('.dialog').dialog({
			autoOpen: false,
			bgiframe: true,
			modal: true,
			width: 'auto',
			show: 'highlight',
			hide: 'highlight'
		});
	};
	$(prepare);
	function get_params(div) {
		var params = {};
		$(div + ' .ui-selected').each(function(c, e) {
			params['stories[' + c + '].id'] = $('.hidden', e).text();
		});
		params['iteration.id']=${iteration.id};
		return params;
	}
	function modifyStories(div, logic) {
		var params = get_params(div);

		$.ajax({
			url: logic,
			data: params,
			success: function(data) {
				$('#stories ol').html($('#stories ol', data).html());
				$('#backlog ol').html($('#backlog ol', data).html());
				prepare();
			}
		});
	}
	function add_stories() {
		modifyStories('#backlog', '<c:url value="/iteration/addStories/"/>');	
	}
	function remove_stories() {
		modifyStories('#stories', '<c:url value="/iteration/removeStories/"/>');
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
<div id="stories">
	<h2>Stories <a href="#" onclick="return show_help()">?</a></h2>
	<ol id="stories_list" class="selectable board">
		<c:if test="${not empty iteration.stories}">
			<c:forEach items="${iteration.stories}" var="story" varStatus="s">
				<li class="story" id="stories_${s.count}" name="${story.name }" ondblclick="open_dialog(${story.id})">
					<p>${story.name }</p>
					<span class="hidden">${story.id }</span>
					<div id="dialog_${story.id }" class="dialog" title="${story.name }">
						${story.description }						
					</div>
				</li>
			</c:forEach>
		</c:if>
	</ol>
</div>
<div id="backlog">
	<h2>BackLog</h2>

	<ol id="backlog_list" class="selectable board">
		<c:if test="${not empty otherStories}">
			<c:forEach items="${otherStories}" var="story" varStatus="s">
				<li class="story" id="backlog_${s.count}" name="${story.name }" ondblclick="open_dialog(${story.id})">
					<p>${story.name }</p>
					<span class="hidden">${story.id }</span>
					<div id="dialog_${story.id }" class="dialog" title="${story.name }">
						${story.description }						
					</div>
				</li>
			</c:forEach>
		</c:if>
	</ol>
</div>

<a href="<c:url value="/project/show/${iteration.project.id }/"/>">Back</a>

</calopsita:page>