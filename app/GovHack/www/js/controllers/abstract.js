angular.module('app.controllers.abstract', [])

    //APP CONTROLLER
    .controller('AppCtrl', function ($scope, $rootScope, $ionicModal, $timeout, $auth, $ionicLoading, $ionicPlatform, $intro, $api) {
        console.log("abstract controller run")
        $scope.distance = 0;
        // Form data for the login modal
        $scope.loginData = {};

        $rootScope.update = function() {
            $('#thermo').thermometer('setValue', $scope.distance);
        };

        // Create the login modal that we will use later
        $ionicModal.fromTemplateUrl('templates/mLogin.html', {
            scope: $scope
        }).then(function (modal) {
            $scope.mLogin = modal;
        });

        // Create the thermo modal that we will use later
        $ionicModal.fromTemplateUrl('templates/mThermo.html', {
            scope: $scope
        }).then(function (modal) {
            $scope.mThermo = modal;
        });

        // Create the intro modal that we will use later
        $ionicModal.fromTemplateUrl('templates/mIntro.html', {
            scope: $scope
        }).then(function (modal) {
            $scope.mIntro = modal;
        });

        // Create the intro modal that we will use later
        $ionicModal.fromTemplateUrl('templates/mTruffle.html', {
            scope: $scope
        }).then(function (modal) {
            $scope.mTruffle = modal;
        });

        // Triggered in the login modal to close it
        $scope.close = function () {
            $scope.mLogin.hide();
            $scope.mThermo.hide();
            $scope.mIntro.hide();
            $scope.mTruffle.hide();
        };

        // Open the login modal
        $scope.login = function () {
            $scope.mLogin.show();
        };

        // Open the thermo modal
        $scope.thermo = function () {
            $scope.mThermo.show();
            initialize(); //initialize the thermometer when modal is called
        };

        // Open the intro modal
        $scope.intro = function () {
            $scope.mIntro.show();
        };

        // Open the login modal
        $scope.showTruffle = function (loc) {
            $scope.truffleContext = loc;
            $scope.mTruffle.show();
        };

        // Perform the login action when the user submits the login form
        $scope.doLogin = function () {
            console.log('Doing login', $scope.loginData);
            $auth.login($scope.loginData);
        };

        $scope.onRange = function (nearables) {
            console.log(JSON.stringify(nearables));
            var dist = nearables.beacons[0].distance;
            $scope.distance = 100 - dist;
            $rootScope.update();
            console.log($scope.distance);
        }

        $scope.inRange = function () {

            return ($scope.distance > 98);
        }

        $scope.onError = function (errorMessage) {
            console.log('Range error: ' + errorMessage);
        }

        $scope.stopScan = function () {
            if (angular.isDefined(estimote)) {
                estimote.beacons.stopRangingBeaconsInRegion(
                {},
                $scope.onRange,
                $scope.onError);
            }
        }

        $scope.startScan = function () {
            // Start ranging.
            estimote.beacons.startRangingBeaconsInRegion(
                {}, // Empty region matches all beacons.
                $scope.onRange,
                $scope.onError
            );
        }

        // loading popup event handler
        $scope.$on('app.loading', function (e) {
            $ionicLoading.show({
                template: '<ion-spinner class="crescent-light"></ion-spinner>'
            });
        });

        // loading popup event handler
        $scope.$on('app.notLoading', function (e) {
            $ionicLoading.hide();
        });

        //A loading hide abstract method which can be accessed from any controller
        $scope.notLoading = function () {
            $rootScope.$broadcast('app.notLoading');
        }

        //Perform the login action when the user submits the login form
        $scope.doLogin = function () {
            console.log($scope.loginData);
            $auth.login($scope.loginData);
        };

        //terms and conditions check to see if terms have been accepted on start up
        $timeout(function () {
            $intro.checkStatus();
        }, 1000);

        //what fires when the TC's haven't been accepted
        $scope.$on('app.welcomeNotPlayed', function (e) {
            console.log("intro not played event fired");
            $scope.intro.show();
        });

        $scope.collect = function (context) {
            $api.getCheckin(context);
        }

        function RGB2HTML(red, green, blue) {
            var decColor = 0x1000000 + blue + 0x100 * green + 0x10000 * red;
            return '#' + decColor.toString(16).substr(1);
        }

        function initialize() {

            $scope.startScan();

            $('#thermo').thermometer({
                height: 400,
                textColour: '#fff',
                tickColour: '#fff',
                liquidColour: function (value) {
                    var red = ~~(value / 100 * 255);
                    var grn = ~~((100 - value) / 100 * 255);
                    return RGB2HTML(red, grn, 0);
                },
                onLoad: function () {
                    updateThermometer();
                }
            });
        };
            
        function updateThermometer() {
            $('#thermo').thermometer('setValue', $scope.distance);
            //window.setTimeout(updateThermometer(), 2500);
        };

        $scope.update = function () {
            updateThermometer();
        }

        $scope.initThermo = function () {
            initialize();
        };

        console.log($scope, "got to the end");

        // !! commented for now because it makes the soft system buttons persist. !!
        // when the hardware back button is pressed, stop the thermometer from scanning (pretty hacky to do it on every back press but its chill cause "if (angular.isDefined(estimote)) { DO THE STOP THING }"
        //$ionicPlatform.onHardwareBackButton(function() {
        //    $scope.stopScan(); 
        //});
    });


