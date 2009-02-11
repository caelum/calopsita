<%@ tag body-content="empty" %><%@ attribute name="value" type="java.lang.String" required="true" %><%
	String context = request.getContextPath();
	String url;	

	if (value.indexOf(':') != -1 || value.charAt(0) != '/' || context.equals("/")) {
		url = value;
	} else {		
		url = context + value;
	}
	out.print(url);
%>