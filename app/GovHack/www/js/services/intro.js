angular.module('app.services.intro', [])


.factory('$intro', function ($rootScope, $localStorage, $tendersService) {
    return {
        checkStatus: function () {
            // Check if terms and conditions have been accepted and fire events
            if (this.isLoggedIn()) {
                $rootScope.$broadcast('app.termsAccepted');
            } else {
                $rootScope.$broadcast('app.termsNotAccepted');
            }
        },
        isLoggedIn: function () {
            if ($localStorage.get("acceptedTCs")) { return true }
            else { return false }
        }
    }
});;

