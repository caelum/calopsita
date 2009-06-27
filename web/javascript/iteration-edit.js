var message;
var imgPath;
var dateFormat;
function initialize(msg, imagePath, dateFmt) {
	message = msg;
	imgPath = imagePath;
	dateFormat = dateFmt;
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
                minlength : 4
            },
            "iteration.startDate" : {
                date : true
            },
            "iteration.endDate" : {
                date : true,
                validateRange : true
            }
        }
    });

    $(".datepicker").datepicker( {
        dateFormat : dateFormat,
        showOn : 'button',
        buttonImage : imgPath,
        buttonImageOnly : true
    });
    
    $('[name=iteration.startDate]').change(function limitDate() {
    	$('[name=iteration.endDate]').datepicker('option', 'minDate', $(this).datepicker('getDate'));
    }).change();
    
    $('[name=iteration.endDate]').change(function() {
    	$('[name=iteration.startDate]').datepicker('option', 'maxDate', $(this).datepicker('getDate'));
    }).change();
    
});