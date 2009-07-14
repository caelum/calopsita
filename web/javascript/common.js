function showDialog(title, body) {
	var div = $('<div title="' + title + '"></div>');
	var content = body;
	if ($('#' + body).length > 0)
		content = $('#' + body).clone().show();
	
	div.append(content).dialog({
		bgiframe: true,
		modal: true,
		width: '500px'
	});
}

function ajaxLoad(url, target) {
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			$(target).html(data);
		}
	});
}