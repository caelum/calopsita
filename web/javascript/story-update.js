function confirmStoryDeletion(url, hasSubstories) {
    var msg = {};
    msg['deletion'] = {
        html : 'Are you sure to delete?',
        buttons : {
            'Yes' : true,
            'No' : false
        },
        submit : function(confirm) {
            if (confirm) {
                if (hasSubstories) {
                    $.prompt.nextState();
                    return false;
                } else {
                    window.location.href = url;
                }
            }
        }
    };
    msg['substories'] = {
        html : 'Delete substories also?',
        buttons : {
            'Yes' : true,
            'No' : false
        },
        submit : function(choice) {
            window.location.href = url + "?deleteSubstories=" + choice;
        }
    };
    $.prompt(msg);
}
$( function() {
    function bind() {
        $('form[name="editStory"]').ajaxForm( {
            beforeSubmit : function() {
                $('[id*="story_edit"]:visible').slideToggle("normal");
            },
            success : function(data) {
                $('#stories').html(data);
                bind();
            }
        });
    }
    bind();
});