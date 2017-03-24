$('.container .js-new').click(function () {
    var _self = $(this);
    ajax(_self, function (data) {
        if (data) {
            var row = _self.closest('.row');
            _self.closest('.container').append(getCopyWithCleanInputs(row));
            row.removeClass('new');
            row.find('input[name="key"]').val(data);
        }
    }, "text");
});

$('.container .js-delete').click(function () {
    var _self = $(this);
    ajax(_self, function () {
        _self.closest('.row').remove();
    })
});

$('.container .js-save').click(function () {
    var _self = $(this);
    ajax(_self);
});

$('.container input[name="value"]').keyup(function () {
    $(this).closest('.row').addClass('edited');
});

function error() {
    alert("smth went bad");
}

function ajax(url, button, success, fail) {
    $.post(button.data('url'), button.closest('tr').find('input, select').serialize(), success, fail);
}

function getCopyWithCleanInputs(e) {
    var clone = e.clone(true);
    clone.find('input, select, textarea').val('');
    return clone;
}

function etalonRow() {
    return $('.etalon .row').clone(true);
}