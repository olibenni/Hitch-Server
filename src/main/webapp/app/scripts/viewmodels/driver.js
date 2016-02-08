/**
 * Created by olafurma on 28.10.2015.
 */
define(
    [
        'plugins/router',
        "durandal/app",
        "knockout",
        "scripts/viewmodels/modal"

    ],
    function(router, app, ko, modal) {
        //Add a new passenger to our table
        app.on("new:passenger", function (data) {
           model.addPassenger(data);
        });

        //Passenger has left, need to take him off our table.
        app.on("passenger:left", function (id) {
            var passengersList = model.passengers();

            //Goes backwards to handle duplications (passenger added more then 1 request)
            for(var i = passengersList.length - 1; i >= 0; i--){
                if(passengersList[i].user === id){
                    model.passengers.splice(i, 1);
                }
            }
        });

        //If we are a new driver our table is empty and we set it with all currently active ride requests
        app.on("new:driver", function (data) {
            if(model.passengers().length === 0) {
                data.forEach(function (passenger) {
                    model.addPassenger(passenger);
                });
            }
        });



        var model = {};

        //Holds info off all currently active ride requests
        model.passengers = ko.observableArray([]);

        model.addPassenger = function (passenger) {
            model.passengers.push({
                info: passenger.departure.substring(0,3) + " - " +  passenger.destination.substring(0,3),
                user: passenger.passenger
            });
        };

        //Activates a message dialog to communicate to corresponding passenger
        model.onClick = function(data) {
            modal.show().then(function(text) {
                var reply = {
                    type: "rideAnswer",
                    user : data.user,
                    message : text
                };
                app.stompClient.send("/app/fromDriver", {}, JSON.stringify(reply));
            });
        };

        //When this view model is activated it fires this function
        //it lets the server know that a new driver has arrived and sends it a list of active ride requests
        model.activate = function activate(){
            app.stompClient.send("/app/newDriver", {}, {});
        };

        return model;
    }
);