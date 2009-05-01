function showDialog(title, body) {
	$('<div title="' + title + '">' + body + '</div>').dialog({
		bgiframe: true,
		modal: true,
		width: '500px',
		show: 'highlight',
		hide: 'highlight'
	});
}