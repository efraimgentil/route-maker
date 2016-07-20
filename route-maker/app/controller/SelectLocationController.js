angular.module( moduleName ).controller("SelectLocationController" , [
  "$scope" , "$location" ,  "LocationService",
  function($scope,  $location  , LocationService){

    $scope.init = function(){
      LocationService.query( {} , function(data){
        $scope.locations = data;
      });
    };

  }
]);