<p>
	<label><fmt:message key="card.name"/></label>
	<em>*</em><input type="text" name="card.name" value="${card.name }"/>
</p>
<p>
	<label><fmt:message key="card.description"/></label>
	<em>*</em><textarea name="card.description">${card.description }</textarea>
</p>
<div class="help">
	<p><fmt:message key="help.cardType.add" /></p>
</div>
<p>
	<label><fmt:message key="cardType" /></label>
	<select id="cardType" onchange="selectGadgets(this)">
		<option value=""></option>
		<c:forEach items="${cardTypes}" var="type" >
			<option value="${type.gadgets }">${type.name }</option>
		</c:forEach>
	</select>
</p>
<fieldset>
 		<legend><fmt:message key="gadgets" /></legend>
 		<c:forEach items="${gadgets}" var="gadget" varStatus="s">
  		<input class="gadget" type="checkbox" name="gadgets[${s.index }]" value="${gadget }" id="${gadget }" ${fn:contains(cardGadgets, gadget)? 'checked="checked"':'' }/><fmt:message key="${gadget}" />
 		</c:forEach>
</fieldset>