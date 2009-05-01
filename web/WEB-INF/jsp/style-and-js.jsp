	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="pt-br"/>

	<script type="text/javascript" src="<c:url value="/javascript/jquery-1.3.2.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/jquery.validate.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/javascript/jquery-ui-1.7.1.custom.min.js"/>"></script>
	
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/index.css"/>" />
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smoothness/ui.all.css"/>" />

	<link rel="shortcut icon" href="<c:url value="/images/icon.ico"/>" type="image/x-icon" />

    <script language="javascript" type="text/javascript">
      $.extend($.validator.messages, {
          required: "<fmt:message key="validation.required" />",
          minlength: jQuery.format("<fmt:message key="validation.minlength" />"),
          email: "<fmt:message key="validation.email" />",
          equalTo: "<fmt:message key="validation.equalTo" />",
          date: "<fmt:message key="validation.date" />"
      });
      function toggle(id) {
   		$("#" + id).slideToggle("normal");
   	  }
    </script>