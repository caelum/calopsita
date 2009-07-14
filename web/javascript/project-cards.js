function toggleDescription() {
	$('.description').slideToggle();
}

function selectGadgets(select) {
	$('.gadget').attr('checked', false);
	if ($(select).val()) {
		var gadgets = $(select).val().match(/\[(.*)\]/)[1].split(', ');
		$(gadgets).each(function(k, v) {
			$('#' + v).attr('checked', true);
		});
	}
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
                error : function() {
                    window.location.href = window.location + '../../../';
                }
            });
            $('[name=card.name]').focus();
            $(form).clearForm();
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