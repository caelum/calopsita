;(function($) {
$.fn.selectableAndDraggable = function(options) {
	this.draggable({
		start: function(ev, ui) {
	    	$(this).is(".ui-selected") || $(".ui-selected").removeClass("ui-selected");
			$(this).addClass('ui-selected');
		},
		revert: 'invalid',
		opacity: 0.5,
		helper: function(event) {
			var div = $('<div></div>');
			if (!$(this).is(".ui-selected")) {
				$('.ui-selected').removeClass('ui-selected');
				$(this).addClass('ui-selected');
			}
			var position = $(this).position();
			
			$('.ui-selected').each(function() {
				var clone = $(this).clone().addClass('clone');
				var x = $(this).position().left - position.left;
				var y = $(this).position().top - position.top;
				clone.css({position: 'absolute', left: x, top: y});
				$(div).append(clone);
				
			});
			return div;
		}
	});
	this.click(function() {
		$(this).toggleClass('ui-selected');
	});
}
})(jQuery);

function _ajax_request(url, data, callback, type, method) {
    if (jQuery.isFunction(data)) {
        callback = data;
        data = {};
    }
    return jQuery.ajax({
        type: method,
        url: url,
        data: data,
        success: callback,
        dataType: type
        });
}
