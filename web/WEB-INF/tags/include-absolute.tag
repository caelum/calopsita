<%@ tag body-content="empty" %>
<%@ attribute name="file" required="true" %>
<%@ attribute name="otherFile" required="false" %>
<%@ attribute name="errorMessage" required="false" %>
<%
	if (!file.startsWith("/")) {
		file = request.getRealPath("/" + file);
	}
	if (!otherFile.startsWith("/")) {
		otherFile = request.getRealPath("/" + otherFile);
	}

	java.io.File f = new java.io.File(file);
	if (f.exists())
		out.println(new java.util.Scanner(f).useDelimiter("\\Z").next());
	else {
		f = new java.io.File(otherFile);
		if (f.exists())
			out.println(new java.util.Scanner(f).useDelimiter("\\Z").next());
		else
			out.println(errorMessage);
	}
%>
