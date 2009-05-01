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