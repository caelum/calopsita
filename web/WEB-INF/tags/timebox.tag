<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="date" description="Date for timebox" required="true" type="org.joda.time.LocalDate" %>
<%@ attribute name="prefix" description="Prefix for divs" required="true" type="java.lang.String" %>
<%@ attribute name="sufix" description="Prefix for divs" required="false" type="java.lang.String" %>

<div id="${prefix }${sufix }" class="date_and_title" >
	<div id="${prefix }_title" class="title_date title"><fmt:message key="${prefix}"/></div>
	<div id="${prefix }${sufix }_date" class="date ${prefix }">
	  <div id="${prefix }_year" class="year">${date.year }</div>
	  <div id="${prefix }_day" class="day"><joda:format value="${date }" pattern="dd"/></div>
	</div>
	<div class="title_date title"></div>
</div>