/**
 * Created by olafurma on 28.10.2015.
 */
define(
    [
        'plugins/router',
        "durandal/app",
        "plugins/dialog",
        "scripts/api/rest",
        "knockout"
    ],
    function(router, app, dialog, rest, ko) {

        app.on("driver:reply", function(data) {
            app.showMessage(data, "Svar bílstjóra", ["Samþykkja tilboð", "Neita tilboði"]);
        });

        var model = {
            header      : ko.observable("Veldu brottfarastað"),
            hikerInfo   : {},
            postalCodes : [
                "101 - Miðbær",
                "103 - Kringlan/Hvassaleiti",
                "105 - Austurbær",
                "107 - Vesturbær",
                "108 - Austurbær",
                "109 - Bakkar/Seljarhverfi",
                "110 - Árbær/Selás",
                "111 - Berg/Hólar/Fell",
                "112 - Grafarvogur",
                "113 - Grafarholt",
                "116 - Kjalarnes"
            ]
        };

        // Initializes the onClick as from;
        model.onClick = function(event, target){
            model.from(target.currentTarget.value);
        };


        model.from = function(place){
            model.hikerInfo.from = place;
            model.header("Veldu áfangastað");

            // Alternates the onClick func between from and to functions.
            model.onClick = function(event, target){
                model.to(target.currentTarget.value);
            };
        };

        model.to = function(place){
            model.hikerInfo.to = place;
            model.header("Veldu Brottfarastað");
            model.sendInfo({
                type: "newPassenger",
                passenger : app.user,
                destination : model.hikerInfo.to,
                departure   : model.hikerInfo.from
            });

            // Alternates the onClick func between from and to functions.
            model.onClick = function(event, target){
                model.from(target.currentTarget.value);
            };
        };

        // Sends a message through websocket.
        // Commented out is how to do it by rest using our own front end rest implementation
        // (see api/rest.js)
        model.sendInfo = function(post) {
            //rest.get("/fromPassenger/?info=" + JSON.stringify(post)).then(
            //    function onResolve(data) {
            //        data = JSON.parse(data);
            //        console.log(data);
            //    }
            //);
            app.stompClient.send("/app/fromPassenger", {}, JSON.stringify(post));
        };


        // When passenger leaves (closes or goes from this view) he notifies the server
        model.deactivate = function deactivate(){
            app.stompClient.send("/app/fromPassenger", {}, JSON.stringify(
                {
                    type: "passengerLeft",
                    passenger: app.user,
                    destination : "",
                    departure   : ""
                }
            ))
        };

        return model;
    }
);
