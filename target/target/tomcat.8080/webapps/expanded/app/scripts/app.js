(function( requirejs ) {

   var require = requirejs.config({
        baseUrl: 'app',
        paths: {
            "text"        : "../bower_components/requirejs-text/text",
            "durandal"    : "../bower_components/durandal/js",
            "plugins"     : "../bower_components/durandal/js/plugins",
            "transitions" : "../bower_components/durandal/js/transitions",
            "knockout"    : "../bower_components/knockout.js/knockout",
            "jquery"      : "../bower_components/jquery/jquery.min",
            "stomp"       : "../bower_components/stomp-websocket/lib/stomp.min",
            "sockJS"      : "../bower_components/sockjs/sockjs.min"
        }
   });

    define(
        "hitch-application",
        [
            'durandal/system',
            'durandal/app',
            'durandal/viewLocator',
            'plugins/router',
            'scripts/utils/liveConnection',
            'transitions/entrance'
        ],

        function(system, app, viewLocator, router, LiveConnection){
            system.debug(false);
            app.title = "Hitch";

            app.configurePlugins({
                router : true,
                dialog : true
            });

            app.start().then(function() {
                viewLocator.useConvention("scripts/viewmodels", "../views");
                app.setRoot("scripts/viewmodels/shell");
            });
            app.user = Date.now();
            app.LiveConnection = new LiveConnection();
            app.LiveConnection.start();

        }
    )
    requirejs(['hitch-application']);
}( requirejs ));
