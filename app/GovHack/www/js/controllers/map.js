angular.module('app.controllers.map', [])


        .controller('MapCtrl', function ($scope, $ionicLoading, $ionicPopup, $cordovaGeolocation, $api) {


            var map;
            var pois;
            var myloc;
            var locations = [];
            
            // google maps init function. starts on page load
            function initialize() { 

                var iconURLPrefix = 'img/Icons/';
                var mapOptions = {
                 
                    zoom: 15,
                    mapTypeId: google.maps.MapTypeId.ROADMAP,
                    streetViewControl: false,
                    panControl: false,
                    zoomControl: true,
                    mapTypeControl: false,
                    scaleControl: false,
                    overviewMapControl: false

                };
                map = new google.maps.Map(document.getElementById("map"), mapOptions);

                var playerIcon = {
                    //set this as the players localtion icon
                    url: 'img/Icons/location.png',
                    scaledSize: new google.maps.Size(20, 20, "%", "%")
                };

                //setup my location marker
                myloc = new google.maps.Marker({
                    clickable: false,
                    icon: playerIcon,
                    shadow: null,
                    zIndex: 999,
                    map: map
                });

                var infowindow = new google.maps.InfoWindow({
                    maxWidth: 260
                });
                

                // Stop the side bar from dragging when mousedown/tapdown on the map
                google.maps.event.addDomListener(document.getElementById('map'), 'mousedown', function (e) {
                    e.preventDefault();
                    return false;
                });

                $scope.map = map;

            };

            google.maps.event.addDomListener(window, 'load', initialize);

            initialize();

            var watchOptions = {
                frequency: 4000,
                timeout: 3000,
                enableHighAccuracy: true // may cause errors if true
            };

            var doWatch = function () {

                var watch = $cordovaGeolocation.watchPosition(watchOptions);
                watch.promise.then(
                    null,
                    function (error) {
                        $scope.errorMsg = "Error : " + error.message;
                    },
                    function (pos) {
                        var me = new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude);
                        updateMyLoc(me);
                        //centerOnMe();
                    });
            };

            var updateMyLoc = function (me) {
                myloc.setPosition(me);
                myloc.setVisible(true);
           
            };

            var showAlert = function (title, content) {
                $ionicPopup.alert({
                    title: title,
                    content: content
                }).then(function (res) {
                    console.log(title, content);
                });
            };

            // this function used to find gps location and center the map on it 
            var centerOnMe = function () {
                if (!$scope.map) {
                    return;
                }

                $scope.loading = $ionicLoading.show({
                    content: 'Getting current location...',
                    showBackdrop: true
                });

                $cordovaGeolocation
                  .getCurrentPosition({ timeout: 5000, enableHighAccuracy: true })
                  .then(function (pos) {
                    console.log('Got pos', pos);
                    var me = new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude);
                    updateMyLoc(me);
                    $scope.map.setCenter(me);
                    $scope.loading.hide();
                    doWatch();

                  }, function (error) {
                      showAlert('Unable to get location: ' + error.message);
                      $scope.errorMsg = "Error : " + error.message;
                      $scope.loading.hide();
                  });

            };

            var clearMarkers = function () {
                if (!$scope.map) {
                    return;
                }

                $scope.map.markers = null;
            }

            var getMarkers = function () {
                if (!$scope.map) {
                    return;
                }

                clearMarkers();

                $scope.loading = $ionicLoading.show({
                    content: 'Getting some trufls...',
                    showBackdrop: true
                });
                centerOnMe();

                $api.getMarkers(myloc).then(function (result) {
                    console.log(result);
                    locations = result;
                    applyMarkers();
                    $scope.loading.hide();
                });

                var applyMarkers = function() {
                    var iconURLPrefix = 'img/Icons/';

                    var marker;
                    var markers = new Array();
                    var iconCounter = 0;


                    // Add the markers and infowindows to the map
                    for (var i = 0; i < locations.length; i++) {
                        //TODO need to append cat img url
                        var pinIcon = {
                            url: iconURLPrefix + locations[i].poiType + ".png",
                            size: null,
                            origin: null,
                            anchor: null,
                            scaledSize: new google.maps.Size(43, 55, "%", "%")
                        };

                        marker = new google.maps.Marker({
                            //position: new google.maps.LatLng(locations[i][1], locations[i][2]),
                            position: new google.maps.LatLng(locations[i].latitude, locations[i].longitude),
                            animation: google.maps.Animation.DROP,
                            map: map,
                            icon: pinIcon
                        });

                        markers.push(marker);

                        google.maps.event.addListener(marker, 'click', (function (marker, i) {
                            return function () {
                                centerOnMe();
                                $scope.showTruffle(locations[i], myloc);
                                //infowindow.setContent(locations[i].description);
                                //infowindow.open(map, marker);
                            };
                        })(marker, i));

                    }
                }
               

            }

            centerOnMe();

            $scope.getMarkers = function () {
                getMarkers();
            }


        });


