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
function updateRecentCards() {
	var selector = '#cards .story';
	if ($('#cards .story').length > 5)
		selector += ':gt(' + ($('#cards .story').length - 6) + ')';
	$('#recent-cards').html('');
	$(selector).clone().appendTo('#recent-cards');
}

function deleteCards(url, deleteSubcards) {
	$.post(url, {_method: 'DELETE', deleteSubcards: deleteSubcards}, function(data) {
		$('#cards').html(data);
		updateRecentCards();
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
                	$('#cards').html(data);
                	updateRecentCards();
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
    
    function bind() {
        $('form[name="editCard"]').ajaxForm( {
            beforeSubmit : function() {
                $('[id*="card_edit"]:visible').slideToggle("normal");
            },
            success : function(data) {
                $('#cards').html($('#cards', data).html());
                bind();
            }
        });
    }
    bind();
});