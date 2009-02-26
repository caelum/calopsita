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
        },
        messages : {
            "user.login" : {
                required :'You must choose a login',
                minlength :'Your login size must be at least 4'
            },
            "user.name" : {
                required :'You must fill your name',
                minlength :'Your name size must be at least 4'
            },
            "user.email" : {
                required :'You must fill your email',
                email :'Email invalid'
            },
            "user.password" : {
                required :'You must fill your password',
                minlength :'Your password size must be at least 4'
            },
            "user.confirmation" : {
                required :'You must fill your password confirmation',
                equalTo :'Must match with your password'
            }
        }

    });

});