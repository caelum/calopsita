function confirmCardDeletion(url, hasSubcards) {
    var msg = {};
    msg['deletion'] = {
        html : 'Are you sure to delete?',
        buttons : {
            'Yes' : true,
            'No' : false
        },
        submit : function(confirm) {
            if (confirm) {
                if (hasSubcards) {
                    $.prompt.nextState();
                    return false;
                } else {
                	$.delete_(url, {}, function(data) {
                		window.location.reload();
                	});
                }
            }
        }
    };
    msg['subcards'] = {
        html : 'Delete subcards also?',
        buttons : {
            'Yes' : true,
            'No' : false
        },
        submit : function(choice) {
        	$.delete_(url, {deleteSubcards: choice}, function(data) {
        		window.location.reload();
        	});
        }
    };
    $.prompt(msg);
}
$( function() {
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