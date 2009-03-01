$(document).ready( function() {
    $("#form").validate( {
        rules : {
            "user.login" : {
                required :true,
                minlength :4
            },
            "user.name" : {
                required :true,
                minlength :4
            },
            "user.email" : {
                required :true,
                email :true
            },
            "user.password" : {
                required :true,
                minlength :4
            },
            "user.confirmation" : {
                required :true,
                equalTo :"#password"
            }
        }
    });

});