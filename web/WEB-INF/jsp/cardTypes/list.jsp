<%@include file="../javascripts.jspf" %>
<script type="text/javascript">
	function toggleForm() {
		$('.formCard').slideToggle();
	}
</script>
<ul id="cardTypes" class="pretty">
<c:forEach items="${cardTypeList}" var="type">
	<li>${type.name }</li>
</c:forEach>
</ul>
<div class="clear">
<a class="formCard" href="javascript:toggleForm()"><fmt:message key="add.cardType"/></a>
<form class="formCard hidden" id="formCard" action="<c:url value="/projects/${project.id }/cardTypes/?selected=2" />" method="post">
	<p>
		<label><fmt:message key="card.name"/></label>
		<em>*</em><input type="text" name="cardType.name"/>
	</p>
	<fieldset title="<fmt:message key="gadgets" />">
  		<legend><fmt:message key="gadgets" /></legend>
  		<c:forEach items="${gadgets}" var="gadget" varStatus="s">
	  		<input type="checkbox" name="cardType.gadgets[${s.index }]" value="${gadget }" id="${gadget }" /><fmt:message key="${gadget}" />
  		</c:forEach>
  	</fieldset>
  	<p>
    	<input class="buttons" type="submit" value="<fmt:message key="add"/>"/>
  		<input class="buttons" type="reset" value="<fmt:message key="cancel"/>" onclick="toggleForm();"/>
  	</p>
</form>
</div>
