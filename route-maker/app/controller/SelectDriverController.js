angular.module( moduleName ).controller("SelectDriverController" , [
  "$scope" , "$location" ,  "DriverService",
  function($scope,  $location  , DriverService){

    $scope.init = function(){
      DriverService.query( {} , function(data){
        $scope.drivers = data;
      });
    };

  }
]);