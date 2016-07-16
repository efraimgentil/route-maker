angular.module( moduleName ).controller("LocationFormController" , [
  "$scope" , "MapService" , "$location" , "$routeParams",
  function($scope,  MapService , $location  , $routeParams){
    var self = this;

    $scope.loc = "";
    $scope.location = { point: {} };

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

      MapService.findPlaceByLocation( new google.maps.LatLng( -3.7484683 , -38.52264100000002 ) , function(data){
        self.addMarkerAndUpdateField(data);
      });
      if( $routeParams.id ){
        /*DriverService.get($routeParams.id , function(data){
          $scope.driver = data;
        });*/
      }else{
        $scope.driver = {};
      }
    }

    $scope.new = function(){

    }

    $scope.searchLocation = function(){
      MapService.findPlaceByName( $scope.loc , function(data){
        self.addMarkerAndUpdateField(data);
      });
    }


  }
]);