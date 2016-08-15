angular.module( moduleName ).controller("StopFormController" , [
  "$scope" , "MapService",
  function($scope,  MapService ){
    var self = this;

    self.addMarker = function( data ){
      if(data){
        MapService.removeAllMarkers();
        MapService.addMarker( data.formatted_address  ,  $scope.stop.point );
      }
    };

    self.updateFields = function( data ){
      var geom = data.geometry;
      $scope.stop.point.lat = geom.location.lat();
      $scope.stop.point.lng = geom.location.lng();
      $scope.stop.pointName = $scope.searchLoc =  data.formatted_address;
      self.addMarker(data);
      $scope.$apply();
    }

    $scope.init = function(){
      $scope.stop = {  point: {}  };
      MapService.initializeMap( $("#mapStop")[0] , null , null , { height : 300 } , function(){

        MapService.addClickEvent( function(data){
          MapService.findPlaceByLocation( data.latLng,  self.updateFields  );
        });
      } );
    };

    $scope.searchLocation = function( searchLoc ){
      MapService.findPlaceByName( searchLoc  , self.updateFields );
    };
  }
]);