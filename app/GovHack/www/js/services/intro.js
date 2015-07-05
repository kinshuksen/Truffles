angular.module('app.services.intro', [])


.factory('$intro', function ($rootScope, $localStorage, $tendersService) {
    return {
        checkStatus: function () {
            // Check if terms and conditions have been accepted and fire events
            if (this.introPlayed()) {
                $rootScope.$broadcast('app.introPlayed');
            } else {
                $rootScope.$broadcast('app.introNotPlayed');
            }
        },
        introPlayed: function () {
            if ($localStorage.get("introPlayed")) { return true }
            else { return false }
        }
    }
});;

