/**
 * Created by olafurma on 28.10.2015.
 */
define(
    [
        "plugins/router",
        "scripts/utils/facebooklogin"
    ],
    function(router, facebooklogin) {
        var model = {
            onConfirm : function() {
                console.log("Logged in");
                router.navigate('passenger-driver');
            }
        };

        return model;
    }
);