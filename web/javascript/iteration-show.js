var cardsUrl;
function initialize(url) {
    cardsUrl = url;
}

function prepare() {
    $('.selectable').selectable( {
        filter : 'li'
    });

    $(".selectable li").selectableAndDraggable();

    $('#todo_cards, #iteration_cards').droppable( {
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
    function fixWidth() {
        var w = $('body').width();
        var h = $('body').height();
        $('#todo_cards, #iteration_cards').css( {
            width : w*0.49,
            float : 'left'
        });
        $('#todo_list, #cards_list').css( {
        	width : w*0.45,
        	height: 500
        });
        $('#done_cards, #backlog').css( {
            width : w*0.49,
            float : 'right'
        });
        $('#done_list, #backlog_list').css( {
        	width : w*0.45,
        	height: 500
        });
    }
    fixWidth();
    $(window).resize(fixWidth);
};
$(prepare);
function get_params(div, status) {
    var params = {};
    $(div + ' .ui-selected').not('.clone').each(function(c, e) {
        params['cards[' + c + '].id'] = e.id.replace("cards_", "");
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
            $('#iteration_cards ul')
                    .html($('#iteration_cards ul', data).html());
            $('#todo_cards ul').html($('#todo_cards ul', data).html());
            $('#done_cards ul').html($('#done_cards ul', data).html());
            $('#backlog ul').html($('#backlog ul', data).html());
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