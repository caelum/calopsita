<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="Iteration" bodyClass="iteration" css="/css/iteration.css">

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
	$(function() {
		$('.selectable').selectable({
			filter:'li'
		});

		$(".selectable li").draggable({
			start: function(ev, ui) {
		    	$(this).is(".ui-selected") || $(".ui-selected").removeClass("ui-selected");
				$(this).addClass('ui-selected');
			},
			revert: 'invalid',
			helper: function() {
				var div = $('<div></div>');
				if ($(this).is(".ui-selected")) {
					$('.ui-selected').each(function() {
						$(div).append($(this).clone());
					});
				} else {
					$('.ui-selected').removeClass('ui-selected');
					$(this).addClass('ui-selected');
					$(div).append($(this).clone());
				}
				return div;
			}
		});
		$('.selectable li').click(function() {
			$(this).toggleClass('ui-selected');
		});

		$('#stories').droppable({
			accept: '.backlog_story',
			drop: add_stories
		});
	});
	
	function add_stories() {
		var params = "";
		$('#backlog .ui-selected').each(function(c, e) {
			params = params + 'stories[' + c + '].id=' + $('.hidden', e).text() + '&';
		});
		params = params + 'iteration.id=' + ${iteration.id};

		window.location = '<c:url value="/iteration/addStories/"/>?' + params;
	}
</script>
<div id="stories">
	<c:if test="${not empty iteration.stories}">
		<h3>Stories</h3>
		<ul class="selectable">
			<c:forEach items="${iteration.stories}" var="story" varStatus="s">
				<li id="story${s.count}">${story.name }</li>
			</c:forEach>
		</ul>
	</c:if>
</div>
<div id="backlog">
	<c:if test="${not empty otherStories}">
		<h2>BackLog</h2>

		<ul class="selectable">
			<c:forEach items="${otherStories}" var="story">
				<li class="backlog_story" id="backlog_${s.count}" name="${story.name }">${story.name }<span class="hidden">${story.id }</span></li>
			</c:forEach>
		</ul>
		<input id="add-story" type="button" value="Add" onclick="add_stories()"/>
	</c:if>
</div>

<a href="<c:url value="/project/show/${iteration.project.id }/"/>">Back</a>

</calopsita:page>