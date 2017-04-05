$('.container .js-new').click(function () {
    var etalon = $(this).closest('.container').find('.etalon');
    var newLine = etalon.clone(true).removeClass('etalon');
    etalon.before(newLine);
    console.log(newLine.find('input[name=value]'));
    newLine.find('input[name=value]').focus();
});

$('.container .js-delete').click(function () {
    var _self = $(this);
    ajax(_self, function () {
        _self.closest('.input-group').remove();
    })
});

$('.container .js-save').click(function () {
    var _self = $(this);
    ajax(_self, function (data) {
        var row = _self.closest('.input-group');
        row.find('input[name="key"]').val(data);
        row.removeClass('edited');
    });
});

$('.container input[name="value"]').keyup(function () {
    $(this).closest('.input-group').addClass('edited');
});

function ajax(button, success, fail) {
    $.post(button.data('url'), button.closest('.input-group').find('input, select').serialize(), success, fail);
}