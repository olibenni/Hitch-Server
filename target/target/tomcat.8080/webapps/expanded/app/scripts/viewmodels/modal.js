/**
 * Created by olafurma on 25.11.2015.
 */
define(
    [
        'plugins/dialog',
        'knockout'
    ],
    function (dialog, ko) {

    var modal = function() {
        this.input = ko.observable('');
    };

    modal.prototype.ok = function() {
        dialog.close(this, this.input());
    };

    modal.show = function(){
        return dialog.show(new modal());
    };

    return modal;
});