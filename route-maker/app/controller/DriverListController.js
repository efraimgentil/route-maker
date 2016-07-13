angular.module( moduleName ).controller("DriverListController" , [
  "$scope" , "DriverService" , "$location",
  function($scope,  DriverService , $location ){

    $scope.init = function(){
      DriverService.query({} , function(data){
        $scope.drivers = data;
      });
    }

    $scope.new = function(){
        
    }


  }
]);