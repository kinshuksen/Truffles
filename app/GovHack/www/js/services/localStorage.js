angular.module('app.services.localStorage', [])

//Angular local storage service
.factory('$localStorage', ['$window', function($window) {
    return {
        set: function(key, value) {
            $window.localStorage[key] = value;
        },
        get: function(key, defaultValue) {
            return $window.localStorage[key] || defaultValue;
        },
        setObject: function(key, value) {
            $window.localStorage[key] = JSON.stringify(value);
        },
        getObject: function(key) {
            return JSON.parse($window.localStorage[key] || '{}');
        }
    }
}])

.factory('$intro', function($rootScope, $localStorage, $api) {
  return {
    checkStatus: function() {
      // Check if terms and conditions have been accepted and fire events
      if(this.isLoggedIn()) {
         $rootScope.$broadcast('app.introPlayed');
      } else {
        $rootScope.$broadcast('app.introNotPlayed');
      }
    },
    isLoggedIn: function() {
      if($localStorage.get("playedIntro")){return true}
      else{return false}
    }
  }
});



