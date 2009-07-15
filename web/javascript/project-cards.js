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
    
});