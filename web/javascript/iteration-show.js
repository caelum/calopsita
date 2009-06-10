var cardsUrl;
function initialize(url) {
    cardsUrl = url;
}

function timeline(daysBetweenTodayAndStartDate, daysBetweenEndDateAndToday,
        daysBetweenEndDateAndStartDate) {
    if (daysBetweenTodayAndStartDate == 0) {
        $('#start').hide();
        $('#start_today_line').hide();
        $('#today_start_title').html('Start');
    }
    if (daysBetweenEndDateAndToday == 0) {
        $('#end').hide();
        $('#start_end_line').hide();
        $('#today_start_title').html('End');
    }
    if (daysBetweenTodayAndStartDate == 0 && daysBetweenEndDateAndToday == 0) {
        $('#today_start_title').html('Start / End');
    }
    if (!(daysBetweenTodayAndStartDate > 0)) {
        $('#start_today_line').css( {
            'width' : 300
        });
        $('#start_end_line').css( {
            'width' : 300
        });

        if (isNaN(daysBetweenEndDateAndToday)) {
            $('#end_date').html('?');
        }
        if (isNaN(daysBetweenTodayAndStartDate)) {
            $('#start_date').html('?');
        }
    }
    if (daysBetweenTodayAndStartDate > 0 && !(daysBetweenEndDateAndToday < 0)) {
        if (!isNaN(daysBetweenEndDateAndToday)) {
            $('#start_today_line')
                    .css(
                            {
                                'width' : 600 * ((daysBetweenTodayAndStartDate - 1) / daysBetweenEndDateAndStartDate)
                            });
            $('#start_end_line')
                    .css(
                            {
                                'width' : 600 * ((daysBetweenEndDateAndToday - 1) / daysBetweenEndDateAndStartDate)
                            });
        }
    }
    if (!(daysBetweenTodayAndStartDate > 0)
            && !(daysBetweenEndDateAndToday < 0)) {
        $('#start').css( {
            'float' : 'right'
        });
        $('#end').css( {
            'float' : 'right'
        });
        $('#start_today_line').css( {
            'float' : 'right'
        });
        $('#start_today_line').css( {
            'border-width' : 0
        });
        if (!(isNaN(daysBetweenEndDateAndToday) && isNaN(daysBetweenTodayAndStartDate))) {
            $('#start_today_line')
                    .css(
                            {
                                'width' : -600
                                        * ((daysBetweenTodayAndStartDate + 1) / daysBetweenEndDateAndToday)
                            });
            $('#start_end_line')
                    .css(
                            {
                                'width' : 600 * ((daysBetweenEndDateAndToday - 1) / daysBetweenEndDateAndToday)
                            });
        }
    }
    if (daysBetweenEndDateAndToday < 0) {
        $('#start_today_line').hide();
        $('#today_start').hide();
        $('#today_end').show();
        $('#today_end_line').show();
        $('#start_end_line')
                .css(
                        {
                            'width' : 600 * ((daysBetweenEndDateAndStartDate - 1) / daysBetweenTodayAndStartDate)
                        });
        $('#today_end_line')
                .css(
                        {
                            'width' : -600
                                    * ((daysBetweenEndDateAndToday + 1) / daysBetweenTodayAndStartDate)
                        });
    }
}

function prepare() {
    $('.selectable').selectable( {
        filter : 'li'
    });

    $(".selectable li").selectableAndDraggable();

    $('#todo_cards').droppable( {
        accept : '.card',
        tolerance : 'pointer',
        drop : todo_cards
    });
    $('#done_cards').droppable( {
        accept : '.card',
        tolerance : 'pointer',
        drop : done_cards
    });
    $('#backlog').droppable( {
        accept : '.card',
        tolerance : 'pointer',
        drop : remove_cards
    });
    $('.dialog').dialog( {
        autoOpen : false,
        bgiframe : true,
        modal : true,
        width : '500px',
        show : 'highlight',
        hide : 'highlight'
    });

    function fixWidth() {
        var width = $('body').width();
        $('#todo_cards').css( {
            width : 0.48 * width,
            'float' : 'left'
        });
        $('#done_cards').css( {
            width : 0.48 * width,
            'float' : 'right'
        });
    }
    fixWidth();
    $(window).resize(fixWidth);
};
$(prepare);
function get_params(div, status) {
    var params = {};
    $(div + ' .ui-selected').not('.clone').each( function(c, e) {
        params['cards[' + c + '].id'] = $('span', e).text();
        params['cards[' + c + '].status'] = status;
    });
    return params;
}
function modifyCards(div, status, method) {
    var params = get_params(div, status);
    params['_method'] = method;
    $.ajax( {
        type : 'POST',
        url : cardsUrl,
        data : params,
        success : function(data) {
            $('#todo_cards ol').html($('#todo_cards ol', data).html());
            $('#done_cards ol').html($('#done_cards ol', data).html());
            $('#backlog ol').html($('#backlog ol', data).html());
            prepare();
        }
    });
}
function todo_cards() {
    modifyCards('.selectable', 'TODO', 'POST');
}
function done_cards() {
    modifyCards('.selectable', 'DONE', 'POST');
}
function remove_cards() {
    modifyCards('.cards', 'TODO', 'DELETE');
}
function show_help() {
    $('#help').dialog('open');
    return false;
}
function open_dialog(id) {
    $('#dialog_' + id).dialog('open');
}