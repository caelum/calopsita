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
                target : "#cards",
                resetForm : true,
                error : function() {
                    window.location.href = window.location + '../../../';
                }
            });
            $('[name=card.name]').focus();
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
    
});