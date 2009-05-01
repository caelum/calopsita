$( function() {
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

});