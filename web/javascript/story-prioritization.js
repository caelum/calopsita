var max_priority = 0;
var stories;
function initialize(storiesArray) {
    stories = storiesArray;
}
function storyCard(name, description, id, count, priority) {
	var li = $('<li class="story" name="' + name + '" title="' + description.substring(0,40) + '..."><p>' + name + '</p></li>');
	li.append('<input type="hidden" name="stories[' + count + '].id" value="' + id + '" />');
	li.append('<input class="priority" type="hidden" name="stories[' + count + '].priority" value="' + priority + '" />');
	li.dblclick(function() {
		showDialog(name, description);
	});
	return li;
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

function getOrCreateUl(priority) {
	if (priority > max_priority + 1) getOrCreateUl(priority - 1);
	if (priority > max_priority) max_priority = priority;
	
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
$(function() {
	for (var i in stories) {
		var s = stories[i];
		getOrCreateUl(s.priority).append(storyCard(s.name, s.description, s.id, s.count, s.priority));
	}
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
			var div = getOrCreateUl(max_priority + 1);
			moveSelectedTo(div);
			bind();
		}
	});
	$(window).resize(changeWidth);
    changeWidth();
});
