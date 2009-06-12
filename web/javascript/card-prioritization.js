var max_priority = 0;
var infinityText;
var prioritizeUrl;
function initialize(infinityPriority, url) {
	infinityText = infinityPriority;
	prioritizeUrl = url;
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
	var data = {};
	$('.ui-selected:not(.clone) .priority').each( function() {
		this.value = div.attr('priority');
	});
	$('.ui-selected:not(.clone) input').each( function() {
		data[this.name] = this.value;
	});

	$.ajax({
		type: 'POST',
		url: prioritizeUrl,
		data: data,
		success: function() {
			$('.ui-selected:not(.clone)').each( function() {
				div.append(this);
				$(this).removeClass('ui-selected');
			});
		},
		error: function() {
			showDialog("Error", "An error occurred");
		}
	});
}
function fixParameters() {
	$('.card').each(function(c, li) {
		$('input', li).each(function(i, input) {
			input.name = input.name.replace('#', '' + c);
		});
	});
	$('.undo').each(function(c, li) {
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
	if ($('ul.board').length > 0)
		max_priority = $('ul.board').length - 1;
}

$(function() {
	fixParameters();
	fixInfinityPriority();
	fixPriorityLevels();
	function bind() {
		$('.board').not('#lowerPriority').droppable({
			accept: function (element) {
				return $(element).is('.card') && $(element).parents('#' + this.id).length == 0;
			},
			tolerance: 'pointer',
			drop: function(event, ui) {
				moveSelectedTo($(this));
			}
		});
	}
	$('#undoForm').ajaxForm( {
        success : function() {
            window.location.reload();
        }
    });
	bind();
	$('#prioritizationForm').selectable({filter: '.card'});
	$('.card').selectableAndDraggable();
	
	$('#lowerPriority').droppable({
		accept: '.card',
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
