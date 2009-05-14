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
            	window.location.href = url;
            }
        }
    };
    $.prompt(msg);
}
