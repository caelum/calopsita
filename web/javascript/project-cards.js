function toggleDescription(el) {
	if (el)
		$('.description', el).slideToggle();
	else
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

function deleteCards(url, deleteSubcards) {
	$.post(url, {_method: 'DELETE', deleteSubcards: deleteSubcards}, function(data) {
		$('#cards').html(data);
	});
}
function confirmCardDeletion(url, hasSubcards) {
    var msg = {};
    msg['deletion'] = {
        html : 'Are you sure to delete?',
        buttons : { 'Yes' : true, 'No' : false },
        submit : function(confirm) {
            if (confirm) {
                if (hasSubcards) {
                    $.prompt.nextState();
                    return false;
                } else {
                	deleteCards(url, false);
                }
            }
        }
    };
    msg['subcards'] = {
        html : 'Delete subcards also?',
        buttons : { 'Yes' : true, 'No' : false },
        submit : function(choice) { 
        	deleteCards(url, choice);
        }
    };
    $.prompt(msg);
}
$( function() {
	$("[name=card.description]").keypress(function() {
		if (this.value.length > 1024) {
			this.value = this.value.substring(0, 1024);
		}
	});
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
                error : function() {
                    window.location.href = window.location + '../../../';
                },
                success: function(data) {
                	$('#recent-cards').replaceWith(data);
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