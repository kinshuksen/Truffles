angular.module('app.services.auth', [])


.factory('$auth', function ($rootScope, $localStorage, $api) {
    return {
        checkLogin: function () {
            // Check if logged in and fire events
            if (this.isLoggedIn()) {
                $rootScope.$broadcast('app.loggedIn');
            } else {
                $rootScope.$broadcast('app.loggedOut');
            }
        },
        isLoggedIn: function () {
            if ($localStorage.get("userID") != 0) { return true }
            else { return false }
        },
        login: function (data) {
            $api.postRegister(data)
                  .then(function (data) {
                      console.log(data);
                      if (data.AckCode == 0) {
                          //display a message to say auth failed
                          $rootScope.$broadcast('app.authError');
                      }
                      else {
                          //much success so store this Id
                          $localStorage.set("userID", data.ResponseObject.TenderUserId);
                          $api.updateDeviceToken($localStorage.get("regID"), $localStorage.get("deviceType"));
                          $rootScope.$broadcast('app.loggedIn');
                      }
                  },
                  function (error) {
                      console.log("fail", error);
                      $rootScope.$broadcast('app.error');
                  });
        },
        //not currently used but kept it here for completeness, and if we decide to allow for dis-associate in future
        logout: function (user, pass) {
            // Same thing, log out user
            $rootScope.$broadcast('app.loggedOut');
        }
    }
});

