// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.controllers' is found in controllers.js
angular.module('app', ['ionic',
    'app.controllers.abstract',
    'app.controllers.hotcold',
    'app.controllers.map',
    'app.controllers.auth',
    'app.services.auth',
    'app.services.startup',
    'app.services.intro',
    'app.services.localStorage',
    'app.services.api',
    'app.directives',
    'ngCordova'
])

.run(function ($ionicPlatform, $cordovaDevice, $http, $rootScope, $localStorage, $api, $filter) {

    //what platform are we running today?
    var isWebView = ionic.Platform.isWebView();
    var isIPad = ionic.Platform.isIPad();
    var isIOS = ionic.Platform.isIOS();
    var isAndroid = ionic.Platform.isAndroid();
    var isWindowsPhone = ionic.Platform.isWindowsPhone();

    function successCallback(successMessage) {
        console.log(successMessage);
    }

    function errorCallback(errorMessage) {
        console.log(errorMessage);
    }

    $ionicPlatform.on('resume', function () {
        if (isAndroid) {
            Immersify.enableSticky(successCallback, errorCallback);
        }
    });

    $ionicPlatform.ready(function () {

        if (isIOS) {
            $localStorage.set("deviceType", "iOS");
            var config = {
                "badge": true,
                "sound": true,
                "alert": true
            }
        }

        if (isAndroid) {
            $localStorage.set("deviceType", "Android");
            
            Immersify.enableSticky(successCallback, errorCallback);
        }

        // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
        // for form inputs)
        if(window.cordova && window.cordova.plugins.Keyboard) {
          cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
        }
        if(window.StatusBar) {
          // org.apache.cordova.statusbar required
          StatusBar.styleDefault();
        }
    
    });

})

.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider

    .state('app', {
      url: "/app",
      abstract: true,
      templateUrl: "templates/menu.html",
      controller: 'AppCtrl'
    })

    .state('app.map', {
      url: "/map",
      views: {
        'menuContent' :{
          templateUrl: "templates/map.html",
          controller: 'MapCtrl'
        }
      }
    })

    .state('app.thermo', {
      url: "/mThermo",
      views: {
        'menuContent' :{
          templateUrl: "templates/mThermo.html",
          controller: 'ThermoCtrl'
        }
      }
    });
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/app/map');
});

