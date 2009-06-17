function toggleDescription() {
	$('.description').slideToggle();
}
$( function() {
    $("#cardForm").validate( {
        rules : {
            "card.name" : {
                required : true,
                minlength : 4
            },
            "card.description" : {
                required : true,
                minlength : 8
            }
        },
        submitHandler : function(form) {
            $(form).ajaxSubmit( {
            	target: '#cards',
                resetForm : true,
                error : function() {
                    window.location.href = window.location + '../../../';
                }
            });
            $('[name=card.name]').focus();
            return false;
        }
    });
    $("#editCard").validate( {
        rules : {
            "card.name" : {
                required : true,
                minlength : 4
            },
            "card.description" : {
                required : true,
                minlength : 8
            }
        }
    });
        
    $("#colaborator").validate( {
        rules : {
            "colaborator.login" : {
                required : true
            }
        }
    });
    
});