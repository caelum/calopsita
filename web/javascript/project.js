$(document).ready( function() {
	$("#addProject").validate( {
        rules : {
            "project.name" : {
                required :true,
                minlength :4
            },
            "project.description" : {
                required :true,
                minlength :10
            }
        }
    });
	
	$("#addStory").validate( {
        rules : {
            "story.name" : {
                required :true,
                minlength :4
            },
            "story.description" : {
                required :true,
                minlength :8
            }
        },
		submitHandler: function(form) {
			$(form).ajaxSubmit({
				target: "#stories",
				clearForm: true
			});
			return false;
		}
    });
	
	$("#addColaborator").validate( {
        rules : {
            "colaborator.login" : {
                required :true
            }
        }
    });
	
	$("#addIteration").validate( {
        rules : {
            "iteration.goal" : {
                required :true,
                minlength :4
            },
            "iteration.startDate" : {
                date: true
            },
            "iteration.endDate" : {
                date: true
            }
        }
    });
	
	$(".datepicker").datepicker({dateFormat: 'mm/dd/yy', showOn: 'button', buttonImage: document.location + '../../../images/calendar.gif', buttonImageOnly: true});
});