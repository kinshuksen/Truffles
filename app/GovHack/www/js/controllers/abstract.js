angular.module('app.controllers.abstract', [])

    //APP CONTROLLER
        .controller('AppCtrl', function ($scope, $rootScope, $ionicModal, $timeout, $auth, $ionicLoading) {
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

            // Triggered in the login modal to close it
            $scope.close = function () {
                $scope.mLogin.hide();
                $scope.mThermo.hide();
                $scope.mIntro.hide();
            };

            // Open the login modal
            $scope.login = function () {
                $scope.mLogin.show();
            };

            // Open the thermo modal
            $scope.thermo = function () {
                $scope.mThermo.show();
            };

            // Open the intro modal
            $scope.intro = function () {
                $scope.mIntro.show();
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

            $scope.onError = function (errorMessage) {
                console.log('Range error: ' + errorMessage);
            }

            $scope.stopScan = function () {
                if (angular.isDefined(estimote)) {
                    estimote.beacons.stopRangingBeaconsInRegion(
                    {},
                    onRanges,
                    onError);
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

        });


