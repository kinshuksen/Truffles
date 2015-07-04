angular.module('app.controllers.auth', [])

.controller('AuthCtrl', function ($scope, $cordovaOauth, $api) {
    $scope.facebookRegister = function(clientId) {
        $cordovaOauth.facebook(clientId, ["email"]).then(function(result) {
            // results
        }, function(error) {
            // error
        });
    }
    $scope.googleRegister = function (clientId) {
        //TODO appscope
        $cordovaOauth.google(clientId, appScope).then(function(result) {
            // results
        }, function(error) {
            // error
        });
    }
    $scope.truflrRegister = function (context) {
        $api.truflrRegister(context);
    }

  
});
       


