angular.module( moduleName ).controller("DriverFormController" , [
  "$scope" , "DriverService" , "$location", "$routeParams" , "MapService",
  function($scope,  DriverService , $location , $routeParams , MapService ){
    var self = this;

    self.addMarkerAndUpdateField = function( data ){
      if(data){
        var geom = data.geometry;
        $scope.driver.location.point.lat = geom.location.lat();
        $scope.driver.location.point.lng = geom.location.lng();
        $scope.driver.location.pointName = $scope.searchLoc =  data.formatted_address;
        MapService.removeAllMarkers();
        MapService.addMarker( data.formatted_address  ,  $scope.driver.location.point );
        $scope.$apply();
      }
    }

    $scope.init = function(){
      MapService.initializeMap( $("#map")[0] , null , null , { height : 300 } , function(){
        if( $routeParams.id ){
          DriverService.get($routeParams.id , function(data){
            $scope.driver = data;
            if($scope.driver.location){
              MapService.findPlaceByLocation( new google.maps.LatLng( $scope.driver.location.point.lat, $scope.driver.location.point.lng ) , function(data){
                self.addMarkerAndUpdateField(data);
              });
            }
          });
        }else{
          $scope.driver = { location: { point:{} } };
        }
      } );
    }

    $scope.searchLocation = function( searchLoc ){
      MapService.findPlaceByName( searchLoc  , function(data){
        self.addMarkerAndUpdateField(data);
      });
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