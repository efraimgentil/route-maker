angular.module( moduleName ).controller("TestController" , [
  "$scope" , "MapService",
  function($scope,  MapService ){
    var self = this;

    self.addMarker = function( data ){
      if(data){
        MapService.removeAllMarkers();
        MapService.addMarker( data.formatted_address  ,  $scope.stop.point );
      }
    };

    $scope.init = function(){
      $scope.stop = {  point: {}  };
      MapService.initializeMap( $("#map")[0] , null , null , { height : 300 } , function(){
        MapService.addClickEvent( function(data){
          $scope.lat = data.latLng.lat();
          $scope.lng = data.latLng.lng();
          MapService.addMarker( "Place" ,  { lat : $scope.lat , lng : $scope.lng } );
          $scope.$apply();
        });
      } );
    };

    $scope.searchLocation = function( searchLoc ){
      MapService.findPlaceByName( searchLoc  , function(data){
        var geom = data.geometry;
        $scope.stop.point.lat = geom.location.lat();
        $scope.stop.point.lng = geom.location.lng();
        $scope.stop.pointName = $scope.searchLoc =  data.formatted_address;
        self.addMarker(data);
        $scope.$apply();
      });
    };
  }
]);