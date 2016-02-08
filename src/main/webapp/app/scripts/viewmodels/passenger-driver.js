/**
 * Created by olafurma on 28.10.2015.
 */
define(
    [
        'plugins/router',
        "durandal/app",
        "scripts/api/rest"

    ],
    function(router, app, rest) {
        var model = {}
        model.onPassenger = function() {
            router.navigate('passenger');
        };
        model.onDriver = function() {
            router.navigate('driver');
        };
        return model;
    }
);