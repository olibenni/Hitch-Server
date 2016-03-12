/**
 * Created by olafurma on 9.11.2015.
 */
define([
        "durandal/app",
        "sockJS",
        "stomp"
    ],
    function (App) {

        //Sets up a connection between the server and the client.
        //Handles all messages from the server
        var LiveConnection = function () {

            var sock = {};
            var restart = true;
            var heartbeatTimeout = 15000;

            //Handle message from server
            var onmessage = function (e) {

                var data =  JSON.parse(e.body);

                if (data.type) {
                    switch (data.type) {
                        case "newPassenger":
                            App.trigger("new:passenger", data);
                            break;
                        case "rideAnswer":
                            if(parseInt(data.user) === App.user) {
                                App.trigger("driver:reply", data.message);
                            }
                            break;
                        case "passengerLeft":
                            App.trigger("passenger:left", data.passenger);
                            break;
                    }
                } else if(Array.isArray(data)) {
                    App.trigger("new:driver", data);
                } else {
                    console.log("unknown message format");
                }
            };

            //Initializes a connection with the server
            var startConnection = function () {
                sock = new SockJS('/ws', undefined, {"debug": false, "devel": false, "info":{"cookie_needed":true}});
                App.stompClient = Stomp.over(sock);
                App.stompClient.debug = null;

                sock.onclose = function (e) {
                    if (restart === true) {
                        startConnection();
                    }
                    console.error("Websocket: " + e.reason);
                };

                //Listen to /broadcast/message from the server
                App.stompClient.connect({}, function(frame) {
                    App.stompClient.subscribe('/broadcast/message', onmessage)
                })();
            };

            this.start = function () {
                startConnection();
            };

            this.close = function () {
                sock.close();
            };
        };

        return LiveConnection;

    });