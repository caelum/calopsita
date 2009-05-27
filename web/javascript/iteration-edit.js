var message;
function initialize(msg) {
	message = msg;
}

$( function() {
    $.validator.addMethod("validateRange", function(value, element, params) {
    	var start = $('[name=iteration.startDate]').datepicker('getDate');
    	var end = $('[name=iteration.endDate]').datepicker('getDate'); 
    	return this.optional(element) ||
    		end == null ||
    		start == null ||
    		start <= end;
    }, message);
    $("#iteration").validate( {
        rules : {
            "iteration.goal" : {
                required : true,
                minlength : 4,
                validateRange : true
            },
            "iteration.startDate" : {
                date : true
            },
            "iteration.endDate" : {
                date : true//,
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