function validate(form) {
    $('.red').remove();
    var result = true;
    $(':input').each(
            function() {
                if (this.value == "") {
                    result = false;
                    $("<strong class='red'>Cannot be blank.</strong>")
                            .insertAfter(this);
                }
            });

    if ($(':input[name="user.password"]').val() != $(
            ':input[name="confirmation"]').val()) {
        $("<strong class='red'>Confirmation doesn't match</strong>")
                .insertAfter(':input[name="confirmation"]');
    }

    return result;
}