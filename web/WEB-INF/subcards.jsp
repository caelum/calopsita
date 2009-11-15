<%@ taglib tagdir="/WEB-INF/tags" prefix="calopsita" %>

<%-- Nasty workaround, because JSTL doesn't support recursion =( --%>
<calopsita:cards cards="${__subcards}" classes="subcards" description="${__description}"
				listId="subcards_${__id}"/>