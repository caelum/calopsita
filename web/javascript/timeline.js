function timeline(daysBetweenTodayAndStartDate, daysBetweenEndDateAndToday,
        daysBetweenEndDateAndStartDate) {
    $('#start_title').html('Start');
    $('#end_title').html('End');
    $('#today_start_today').html('Today');
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
    if (!(daysBetweenEndDateAndStartDate > 0)) {
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