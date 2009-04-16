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
</script>
<div id="stories">
	<c:if test="${not empty iteration.stories}">
		<h3>Stories</h3>
		<ul id="iterationStories" class="selectable">
			<c:forEach items="${iteration.stories}" var="story" varStatus="s">
				<li id="stories${s.count}">${story.name }</li>
			</c:forEach>
		</ul>
	</c:if>
</div>
<div id="otherStories">
	<c:if test="${not empty otherStories}">
		<h2>Other Stories</h2>

		<ul id="otherStories" class="selectable">
			<c:forEach items="${otherStories}" var="story" varStatus="s">
				<li id="otherStories${s.count}">${story.name }</li>
			</c:forEach>
		</ul>
	</c:if>
	<input id="add-story" type="button" value="Add" onclick="add_stories()"/>
</div>

<a href="<c:url value="/project/show/${iteration.project.id }/"/>">Back</a>

</calopsita:page>