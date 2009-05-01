$( function() {
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
		submitHandler : function(form) {
			$(form).ajaxSubmit( {
				target :"#stories",
				resetForm :true
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
				date :true
			},
			"iteration.endDate" : {
				date :true
			}
		}
	});

	$(".datepicker").datepicker( {
		dateFormat :'mm/dd/yy',
		showOn :'button',
		buttonImage :document.location + '../../../images/calendar.gif',
		buttonImageOnly :true
	});
});