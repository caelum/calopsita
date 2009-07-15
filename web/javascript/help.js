var newbie = true;
var toggleUrl;
function setNewbie(val) {
	newbie = val;
}
function setUrl(val) {
	toggleUrl = val;
}
$(function() {
  	$('<span class="close ui-icon ui-icon-close"></span>')
  		.click(function(e) {
			$(this).parents('.help').hide();
	  	}).prependTo('.help > *');
  	
  	if (newbie)
  		$('.help').show();

	$('#toggleHelp').click(function() {
		if ($('#toggleHelp').is('.newbie'))
			$('.help').hide();
		else
			$('.help').show();
			
		$('#toggleHelp').toggleClass('newbie');
		
		$.get(toggleUrl);
	});
});