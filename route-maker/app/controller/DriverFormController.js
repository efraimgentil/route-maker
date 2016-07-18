angular.module( moduleName ).controller("DriverFormController" , [
  "$scope" , "DriverService" , "$location", "$routeParams",
  function($scope,  DriverService , $location , $routeParams ){

    $scope.init = function(){
      if( $routeParams.id ){
        DriverService.get($routeParams.id , function(data){
          $scope.driver = data;
        });
      }else{
        $scope.driver = {};
      }
    }

    $scope.save = function(){
      DriverService.save( $scope.driver , function(){
        $location.path("/driver");
      });
    }

    $scope.cancel = function(){
      $location.path("/driver");
    }

  }
]);