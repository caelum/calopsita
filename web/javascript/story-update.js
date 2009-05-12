function confirmDeletion(url) {
	$.prompt({'': {
		html: 'Are you sure to delete?',
		buttons: {
			'Yes' : true,
			'No' : false
		},
		submit: function(confirm) {
			if (confirm) {
				window.location.href = url;
			}
		}
	}});
}
$(function() {
	function bind() {
		$('form[name="editStory"]').ajaxForm({
			beforeSubmit: function () {
				$('[id*="story_edit"]:visible').slideToggle("normal");
			},
			success: function(data) {
				$('#stories').html(data);
				bind();
			}
		});
	}
	bind();
});