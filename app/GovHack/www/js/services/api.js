angular.module('app.services.api', [])


.service('$api', function($q, $rootScope, $localStorage, $http, $cordovaDevice, $ionicPlatform, $state, $filter) {

    //SOME BUILD VARIABLES
    //these variables are to be set for each build, every client has a different url and skin
    var client = "ACT"
    //Staging

    var baseUrl = "http://43.229.60.110/truffles";
    //Prod
    //var apiUrl = "https://etendersazure.azurewebsites.net/api/" + client;

    //External Base Url
    var externalBaseUrl = 'https://satenders.solutions.intecgroup.com.au/ets';
    var detailsUrl = externalBaseUrl+'/tender/display/tender-details.do?action=display-tender-details&';
    //Prod details link
    //var detailsLink = 'https://www.tenders.sa.gov.au/tenders/tender/display/tender-details.do?action=display-tender-details&';

    var isInitialised = $localStorage.get('isInitialised');
    var playedIntro = $localStorage.get('playedIntro');

    //check for first a time run and put things in local storage if its a first time run
    if (isInitialised) {
        console.log("Everything is stored... Continue");
        $localStorage.set("clientCode", client);
    }else
    {
        console.log("first time run");
        //get device UID
        $ionicPlatform.ready(function() {
            $localStorage.set("deviceUID", $cordovaDevice.getUUID());
        });
        //init a blank userID
        $localStorage.set("userID", 0);
        //init a blank regID
        $localStorage.set("regID", 0);
        //set the base url for service calls
        $localStorage.set('baseUrl', baseUrl);
        $localStorage.set('detailsUrl', detailsUrl);
        $localStorage.set("isInitialised", true);
        $localStorage.set("refreshTenders", false);

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


    //Get web service response data example
    getMarkers: function(myloc) {
        return GetData(baseUrl + "/poi/getpoitest?", { userID: $localStorage.get("userID"), latitude: myloc.position.A, longitude : myloc.position.F, dist: 10}, false);
    },

    //Post example passing in a JSON obj
    postRegister: function(context) {
        console.log(context);
        var deviceUID = "[{deviceUID: " + $localStorage.get("deviceUID") + "}]";
        return PostData(baseUrl + "/Register", context);
    },

      //Post example passing in a JSON obj
    GetRegister: function (context) {
        console.log(context);
        var deviceUID = "[{deviceUID: " + $localStorage.get("deviceUID") + "}]";
        return GetData(baseUrl + "/Register", context);
    },

    //Get Example
    serviceCheck: function() {
        return GetData(baseUrl+"/ServiceCheck", null);
    }

  }

});