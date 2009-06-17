<script language="javascript" type="text/javascript">
  if ($.validator) {
   $.extend($.validator.messages, {
       required: '<fmt:message key="validation.required" />',
       minlength: jQuery.format('<fmt:message key="validation.minlength" />'),
       email: '<fmt:message key="validation.email" />',
       equalTo: '<fmt:message key="validation.equalTo" />',
       date: '<fmt:message key="validation.date" />'
   });
  }
  function toggle(id) {
	$("#" + id).slideToggle("normal");
  }
</script>