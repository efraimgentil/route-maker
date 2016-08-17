angular.module( moduleName ).controller("RouteListController" , [
  "$scope" , "RouteService" , "$location",
  function($scope,  RouteService , $location ){

    $scope.init = function(){
      RouteService.query({} , function(data){
        $scope.routes = data;
      });
    }

    $scope.new = function(){
        
    }


  }
]);