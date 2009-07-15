var newbie = true;
var url;
function setNewbie(val) {
	newbie = val;
}
function setUrl(val) {
	url = val;
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
		
		$.get(url);
	});
});