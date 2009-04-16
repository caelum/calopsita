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
	});
	
	function add_stories() {
		var params = "";
		$('#otherStories .ui-selected').each(function(c, e) {
			params = params + 'stories[' + c + '].id=' + $('.hidden', e).text() + '&';
		});
		params = params + 'iteration.id=' + ${iteration.id};

		window.location = '<c:url value="/iteration/addStories/"/>?' + params;
	}
</script>
<div id="stories">
	<c:if test="${not empty iteration.stories}">
		<h3>Stories</h3>
		<ol id="iterationStories" class="selectable">
			<c:forEach items="${iteration.stories}" var="story" varStatus="s">
				<li id="stories${s.count}">${story.name }</li>
			</c:forEach>
		</ol>
	</c:if>
</div>
<div id="otherStories">
	<c:if test="${not empty otherStories}">
		<h2>Other Stories</h2>

		<ol id="otherStories" class="selectable">
			<c:forEach items="${otherStories}" var="story" varStatus="s">
				<li id="otherStories${s.count}" name="${story.name }">${story.name }<span class="hidden">${story.id }</span></li>
			</c:forEach>
		</ol>
		<input id="add-story" type="button" value="Add" onclick="add_stories()"/>
	</c:if>
</div>

<a href="<c:url value="/project/show/${iteration.project.id }/"/>">Back</a>

</calopsita:page>