angular.module('app.services.api', [])


.service('$api', function($q, $rootScope, $localStorage, $http, $cordovaDevice, $ionicPlatform, $state, $filter) {

    var baseUrl = "http://43.229.60.110/truffles";
 
    var isInitialised = $localStorage.get('isInitialised');
    var playedIntro = $localStorage.get('playedIntro');
    
    //check for first a time run and put things in local storage if its a first time run
    if (isInitialised) {
        console.log("Everything is stored... Continue");
    }else
    {
        console.log("first time run");
        //get device UID
        $ionicPlatform.ready(function() {
            $localStorage.set("deviceUID", $cordovaDevice.getUUID());
        });
        //init a blank userID
        $localStorage.set("userID", 1);
        //init a blank regID
        $localStorage.set("regID", 0);
        //set the base url for service calls
        $localStorage.set('baseUrl', baseUrl);
        $localStorage.set("isInitialised", true);
        $rootScope.$broadcast("app.introNotPlayed");
    }

    //query data helper method.
    function EncodeQueryData(data)
    {
       var ret = [];
       for (var d in data)
          ret.push(encodeURIComponent(d) + "=" + encodeURIComponent(data[d]));
       return ret.join("&");
    }

    //generic http get method, use me to get stuff from tenders Mobile API
    function GetData(baseUrl, params, isCached) {
      $rootScope.$broadcast('app.loading');
      $http.defaults.cache = isCached;

      var dfd = $q.defer();
      var url;
      if (params !== null) {
        var paramsString = EncodeQueryData(params);
        url = baseUrl + paramsString;
      }
      else {url = baseUrl;}
        console.log("GET: ", url);
      $http.get(url)
      .success(function(data, status, headers, config) {
          dfd.resolve(data);
          $rootScope.$broadcast('app.notLoading');
      })
      .error(function(data, status, headers, config) {
            console.log("THERE WAS AN ERROR GETTING DATA", data, status, headers, config);
            $rootScope.$broadcast('app.notLoading');
      });

      console.log(dfd.promise);
      return dfd.promise;
    }

    //generic http post method, use me to get stuff from tenders Mobile API
    function PostData(baseUrl, params) {

      var dfd = $q.defer();
        console.log("POST: ", baseUrl, params);
          $http.post(baseUrl, params)
          .success(function(data, status, headers, config) {
            return dfd.resolve(data);
          })
          .error(function(data, status, headers, config) {
                console.log("THERE WAS AN ERROR POSTING DATA", data, status, headers, config);
          });
      return dfd.promise;
    }

    function GetLocalData(url) {
      var obj = {data:null};
      $http.get(url).success(function (data) {
        console.log(data);
          obj.data = data;
      });
      return obj;
    }

  return {
    //Get local data example
    getStatusList: function() {
        return GetLocalData("data/categories.json");
    },
   
    getMarkers: function(myloc) {
        return GetData(baseUrl + "/poi/getpois?", { userID: $localStorage.get("userID"), latitude: myloc.position.A, longitude : myloc.position.F, dist: 10}, false);
    },

    getAchievements: function () {
        return GetData(baseUrl + "/user/achievements?", { userId: $localStorage.get("userID") }, false);
    },

    getTruffles: function () {
        return GetData(baseUrl + "/truffle/get_truffle?", { userId: $localStorage.get("userID") }, false);
    },
   
    getCheckin: function (context) {
        var payLoad = [];
        return GetData(baseUrl + "/truffle/bag_truffle?", { poiId: context.poiId, latitude: context.latitude, longitude: context.longitude, name: context.name, userId: $localStorage.get("userID"), poiType: context.poiType}, false);
    },

      //Get web service response data example
    getChallenges: function () {
        return GetData(baseUrl + "/user/challenges?", true);
    },

    //Post example passing in a JSON obj
    postRegister: function(context) {
        console.log(context);
        var payload = {email: context.email, token: null, password: context.password, deviceUUID: $localStorage.get("deviceUID") };
        return GetData(baseUrl + "/user/register?", payload);
    },

      //Post example passing in a JSON obj
    setRegister: function (context) {
        console.log(context);
        var deviceUID = "[{deviceUID: " + $localStorage.get("deviceUID") + "}]";
        return GetData(baseUrl + "user/login", context);
    },



    //Get Example
    serviceCheck: function() {
        return GetData(baseUrl+"/ServiceCheck", null);
    }

  }

});