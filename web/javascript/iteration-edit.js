var message;
function initialize(msg) {
	message = msg;
}

$( function() {
    $.validator.addMethod("greater", function(value, element, params) {
    	return this.optional(element) || $(element).datepicker('getDate') >= $(params).datepicker('getDate');
    }, message);
    $("#iteration").validate( {
        rules : {
            "iteration.goal" : {
                required : true,
                minlength : 4
            },
            "iteration.startDate" : {
                date : true
            },
            "iteration.endDate" : {
                date : true,
                greater: '[name=iteration.startDate]'
            }
        }
    });

    $(".datepicker").datepicker( {
        dateFormat : 'mm/dd/yy',
        showOn : 'button',
        buttonImage : document.location + '../../../images/calendar.gif',
        buttonImageOnly : true
    });
    
    $('[name=iteration.startDate]').change(function() {
    	$('[name=iteration.endDate]').datepicker('option', 'minDate', $(this).datepicker('getDate'));
    });
    $('[name=iteration.endDate]').change(function() {
    	$('[name=iteration.startDate]').datepicker('option', 'maxDate', $(this).datepicker('getDate'));
    });
    
});