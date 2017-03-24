$('.list-container .js-add').click(function (e) {
    $(this).before($('.etalon > *').clone(true));
});

$('.list-container .delete-input').click(function (e) {
    $(this).prev().remove();
    $(this).remove();
});

$('form').submit(function () {
    $('.list-container').each(function () {
        var container = $(this);
        container.find('input[name]').remove();
        $.each($(this).find('input.form-control'), function (i, e) {
            if ($(e).val()) {
                $(e).before($('<input type="hidden" name="' + container.data('path') + '[' + i + ']" value="' + $(e).val() + '">'));
            }
        });
    });
});