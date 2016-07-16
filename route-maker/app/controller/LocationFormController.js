angular.module( moduleName ).controller("LocationFormController" , [
  "$scope" , "MapService" , "$location",
  function($scope,  MapService , $location ){


    $scope.loc = "";
    $scope.location = { point: {} };

    $scope.init = function(){
      MapService.initializeMap( $("#map")[0] , null , null , { height : 300 } );
    }

    $scope.new = function(){

    }

    $scope.searchLocation = function(){
      MapService.findPlace( $scope.loc , function(data){
        if(data){
          geom =  data.geometry;
          $scope.location.point.lat = geom.location.lat();
          $scope.location.point.lng = geom.location.lng();
          $scope.location.point.location =  data.formatted_address;
          $scope.$apply();
        }
      });
    }

  }
]);