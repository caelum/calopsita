var updateStoriesUrl;
var removeStoriesUrl;
var iterationId;
function initialize(iteration, update, remove) {
	iterationId = iteration;
	updateStoriesUrl = update;
	removeStoriesUrl = remove;
}
function showDialog(title, body) {
	$('<div title="' + title + '">' + body + '</div>').dialog({
		bgiframe: true,
		modal: true,
		width: '500px',
		show: 'highlight',
		hide: 'highlight'
	});
}

function prepare() {
	$('.selectable').selectable({
		filter:'li'
	});

	$(".selectable li").selectableAndDraggable();
	

	$('#todo_stories').droppable({
		accept: '.story',
		tolerance: 'pointer',
		drop: todo_stories
	});
	$('#done_stories').droppable({
		accept: '.story',
		tolerance: 'pointer',
		drop: done_stories
	});
	$('#backlog').droppable({
		accept: '.story',
		tolerance: 'pointer',
		drop: remove_stories
	});
	$('.dialog').dialog({
		autoOpen: false,
		bgiframe: true,
		modal: true,
		width: '500px',
		show: 'highlight',
		hide: 'highlight'
	});
	
	function fixWidth() {
		var width = $('body').width();
		$('#todo_stories').css({width: 0.48*width, 'float': 'left'});
		$('#done_stories').css({width: 0.48*width, 'float': 'right'});
	}
	fixWidth();
	$(window).resize(fixWidth);
};
$(prepare);
function get_params(div, status) {
	var params = {};
	$(div + ' .ui-selected').not('.clone').each(function(c, e) {
		params['stories[' + c + '].id'] = $('.hidden', e).text();
		params['stories[' + c + '].status'] = status;
	});
	params['iteration.id']= iterationId;
	return params;
}
function modifyStories(div, status, logic) {
	var params = get_params(div, status);

	$.ajax({
		url: logic,
		data: params,
		success: function(data) {
			$('#todo_stories ol').html($('#todo_stories ol', data).html());
			$('#done_stories ol').html($('#done_stories ol', data).html());
			$('#backlog ol').html($('#backlog ol', data).html());
			prepare();
		}
	});
}
function todo_stories() {
	modifyStories('.selectable', 'TODO', updateStoriesUrl);	
}
function done_stories() {
	modifyStories('.selectable', 'DONE', updateStoriesUrl);	
}
function remove_stories() {
	modifyStories('.stories', 'TODO', removeStoriesUrl);
}
function show_help() {
	$('#help').dialog('open');
	return false;
}
function open_dialog(id) {
	$('#dialog_' + id).dialog('open');
}