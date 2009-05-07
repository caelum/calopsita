var max_priority = 0;
var infinityText;
function initialize(infinityPriority) {
	infinityText = infinityPriority;
}
function changeWidth() {
    var w = $('body').width();
    $('.board').css( {
        width : w * 0.55
    });
    $('.table').css( {
        width : w * 0.45 - 50
    });
}

function showDialog(title, body) {
	var div = $('<div title="' + title + '"></div>');
	div.append($('#' + body).clone().show()).dialog({
		bgiframe: true,
		modal: true,
		width: '500px',
		show: 'highlight',
		hide: 'highlight'
	});
}

function createPriorityLevel() {
	var priority = max_priority++;
	var ul = $('#level_' + priority);
	if (ul.length == 0) {
		$('#board').append('<h2 class="title">Priority ' + priority + '</h2>');
		ul = $('<ul id="level_' + priority + '" class="board" title="Priority ' + priority + '" priority="' + priority + '"></ul>');
		$('#board').append(ul);
		changeWidth();
	}
	return ul;
}
function moveSelectedTo(div) {
    $('.ui-selected').not('.clone').each( function() {
        $(this).children('.priority').val(div.attr('priority'));
        $(this).removeClass('ui-selected');
        div.append(this);
    });
}
function fixParameters() {
	$('.story').each(function(c, li) {
		$('input', li).each(function(i, input) {
			input.name = input.name.replace('#', '' + c);
		});
	});
}
function fixInfinityPriority() {
	var div = $('<div class="table"></div>');
	div.prependTo('#prioritizationForm');
	div.append($('#title_0').html(infinityText));
	div.append($('#level_0').attr('title', infinityText));
}
function fixPriorityLevels() {
	max_priority = $('ul.board').length - 1;
}

$(function() {
	fixParameters();
	fixInfinityPriority();
	fixPriorityLevels();
	function bind() {
		$('.board').not('#lowerPriority').droppable({
			accept: function (element) {
				return $(element).is('.story') && $(element).parents('#' + this.id).length == 0;
			},
			tolerance: 'pointer',
			drop: function(event, ui) {
				moveSelectedTo($(this));
			}
		});
	}
	bind();
	$('#prioritizationForm').selectable({filter: '.story'});
	$('.story').selectableAndDraggable();
	
	$('#lowerPriority').droppable({
		accept: '.story',
		tolerance: 'pointer',
		drop: function(event, ui) {
			var div = createPriorityLevel();
			moveSelectedTo(div);
			bind();
		}
	});
	$(window).resize(changeWidth);
    changeWidth();
});
