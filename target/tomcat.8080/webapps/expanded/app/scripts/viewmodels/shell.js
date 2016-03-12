/**
 * Created by olafurma on 28.10.2015.
 */
define(
    [
        'plugins/router'
    ],
    function (router) {
        return {
            router: router,
            activate: function () {
                router.map([
                    { route: '', title: 'Login', moduleId: 'scripts/viewmodels/login', nav: false },
                    { route: 'passenger-driver', title: 'Hitching or Driving', moduleId: 'scripts/viewmodels/passenger-driver', nav: false },
                    { route: 'passenger', title: 'Where to', moduleId: 'scripts/viewmodels/passenger', nav: false },
                    { route: 'driver', title: 'Passenger list', moduleId: 'scripts/viewmodels/driver', nav: false }
                ]).buildNavigationModel();

                return router.activate();
            }
        };
    }
);

