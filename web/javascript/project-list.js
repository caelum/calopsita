function confirmProjectDeletion(url) {
    var msg = {};
    msg['deletion'] = {
        html : $('#deletion_text').html(),
        buttons : {
            'Yes' : true,
            'No' : false
        },
        submit : function(confirm) {
            if (confirm) {
            	$.post(url, {_method: 'DELETE'}, function(data) {
            		$('#projects').html(data);
            	});
            }
        }
    };
    $.prompt(msg);
}
