<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<%@ attribute name="date" description="Date for timebox" required="true" type="org.joda.time.LocalDate" %>
<%@ attribute name="prefix" description="Prefix for divs" required="true" type="java.lang.String" %>
<%@ attribute name="sufix" description="Prefix for divs" required="false" type="java.lang.String" %>

<div id="${prefix }${sufix }" class="date_and_title" >
	<div id="${prefix }${sufix }_title" class="title_date title"></div>
	<div id="${prefix }${sufix }_date" class="date ${prefix }">
	  <div id="${prefix }_year" class="year">${date.year }</div>
	  <div id="${prefix }_day" class="day"><joda:format value="${date }" pattern="dd"/></div>
	  <div id="${prefix }_month" class="month"><joda:format value="${date}" pattern="MMM" /></div>
	</div>
	<div id="${prefix }${sufix }_today" class="title_date title"></div>
</div>