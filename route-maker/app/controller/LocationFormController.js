angular.module( moduleName ).controller("LocationFormController" , [
  "$scope" , "MapService" , "$location" , "$routeParams", "LocationService",
  function($scope,  MapService , $location  , $routeParams , LocationService){
    var self = this;
    $scope.loc = "";


    console.log(LocationService);

    self.addMarkerAndUpdateField = function( data ){
      if(data){
        geom =  data.geometry;
        $scope.location.point.lat = geom.location.lat();
        $scope.location.point.lng = geom.location.lng();
        $scope.location.point.location =  data.formatted_address;
        MapService.removeAllMarkers();
        MapService.addMarker( data.formatted_address  ,  $scope.location.point );
        $scope.$apply();
      }
    }

    $scope.init = function(){
      MapService.initializeMap( $("#map")[0] , null , null , { height : 300 } );

      if( $routeParams.id ){
        LocationService.get( $routeParams.id, function( data ){
          $scope.location = data;
          MapService.findPlaceByLocation( new google.maps.LatLng( $scope.location.point.lat, $scope.location.point.lng ) , function(data){
            self.addMarkerAndUpdateField(data);
          });
        } );
      }else{
        $scope.location = { point: {} };
      }
    }

    $scope.save = function(){
      LocationService.save( $scope.location , function(data){  console.log(data) } );
    }

    $scope.searchLocation = function(){
      MapService.findPlaceByName( $scope.loc , function(data){
        self.addMarkerAndUpdateField(data);
      });
    }


  }
]);