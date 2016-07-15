angular.module( moduleName ).controller("RouteListController" , [
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