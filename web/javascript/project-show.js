function confirmIterationDeletion(url) {
    var msg = {};
    msg['deletion'] = {
        html : 'Are you sure to delete?',
        buttons : {
            'Yes' : true,
            'No' : false
        },
        submit : function(confirm) {
            if (confirm) {
                window.location.href = url;
            }
        }
    };
    $.prompt(msg);
}

$( function() {
    $("#storyForm").validate( {
        rules : {
            "story.name" : {
                required : true,
                minlength : 4
            },
            "story.description" : {
                required : true,
                minlength : 8
            }
        },
        submitHandler : function(form) {
            $(form).ajaxSubmit( {
                target : "#stories",
                resetForm : true,
                error : function() {
                    window.location.href = window.location + '../../../';
                }
            });
            return false;
        }
    });

    $("#colaborator").validate( {
        rules : {
            "colaborator.login" : {
                required : true
            }
        }
    });

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
                date : true
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