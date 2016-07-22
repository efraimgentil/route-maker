angular.module( moduleName ).controller("StopFormController" , [
  "$scope" , "MapService",
  function($scope,  MapService ){
    var self = this;

    self.addMarker = function( data ){
      if(data){
        MapService.removeAllMarkers();
        MapService.addMarker( data.formatted_address  ,  $scope.driver.home.point );
      }
    }

    $scope.init = function(){
      MapService.initializeMap( $("#map")[0] , null , null , { height : 300 } , function(){


      } );
    }

    $scope.searchLocation = function( searchLoc ){
      MapService.findPlaceByName( searchLoc  , function(data){
        var geom = data.geometry;
        $scope.driver.home.point.lat = geom.location.lat();
        $scope.driver.home.point.lng = geom.location.lng();
        $scope.driver.home.pointName = $scope.searchLoc =  data.formatted_address;
        self.addMarker(data);
        $scope.$apply();
      });
    }

  }
]);