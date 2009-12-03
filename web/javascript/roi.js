function sendROI(roiId, newRoi, span, form){
    $.ajax( {
        type : 'POST',
        url : context + 'roi/' + roiId + '/',
        data : {'card.roiValue':newRoi},
        success: function() {
        	$(span).html(newRoi).show();
        	form.remove();
        },
        error : function() {
            showDialog("Error", "An error occurred");
        }
    });
}

function editROI(roiId, span){
	var form = $('<form class="roiForm" name="roiForm"><input name="newRoi" value="'+ $(span).html() + '"/></form>');
	form.submit(function(){
					sendROI(roiId, this.newRoi.value, span, form);
					return false;
				});
	form.insertAfter($(span));
	$(span).hide();
}