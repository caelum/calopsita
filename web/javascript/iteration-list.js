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
            	$.ajax({
            		type: 'POST',
            		url: url,
            		data: {_method: 'DELETE'},
            		success: function() {
            			window.location.reload();
            		}
            	});
            }
        }
    };
    $.prompt(msg);
}
