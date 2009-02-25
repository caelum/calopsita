<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<calopsita:page title="User" bodyClass="user">
<script type="text/javascript">
	function validate(form) {
		$('.red').remove();
		var result = true;
		$(':input').each(function() {
			if (this.value == "") {
				result = false;
				$("<strong class='red'>Cannot be blank.</strong>").insertAfter(this);
			}
		});

		if ($(':input[name="user.password"]').val() != $(':input[name="confirmation"]').val()) {
			$("<strong class='red'>Confirmation doesn't match</strong>").insertAfter(':input[name="confirmation"]');
		}

		return result;
	}
</script>
<form id="form" action="<c:url value="/user/save/"/>" method="post" onsubmit="return validate(this);">
  <p>Name: <input type="text" name="user.name"/></p>
  <p>Login: <input type="text" name="user.login"/></p>
  <p>Email: <input type="text" name="user.email"/></p>
  <p>Password: <input type="password" name="user.password"/></p>
  <p>Confirmation: <input type="password" name="confirmation"/></p>
  <p><input class="buttons" type="submit" value="Sign Up"/></p>
</form>

<a href="<c:url value="/"/>">Voltar</a>

</calopsita:page>