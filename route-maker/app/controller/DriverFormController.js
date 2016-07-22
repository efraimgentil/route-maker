angular.module( moduleName ).controller("DriverFormController" , [
  "$scope" , "DriverService" , "$location", "$routeParams" , "MapService",
  function($scope,  DriverService , $location , $routeParams , MapService ){
    var self = this;

    self.addMarker = function( data ){
      if(data){
        MapService.removeAllMarkers();
        MapService.addMarker( data.formatted_address  ,  $scope.driver.home.point );
      }
    }

    $scope.init = function(){
      MapService.initializeMap( $("#map")[0] , null , null , { height : 300 } , function(){
        if( $routeParams.id ){
          DriverService.get($routeParams.id , function(data){
            $scope.driver = data;
            if($scope.driver.home){
              $scope.searchLoc = $scope.driver.home.pointName;
              MapService.findPlaceByLocation( new google.maps.LatLng( $scope.driver.home.point.lat, $scope.driver.home.point.lng ) , function(data){
                self.addMarker(data);
              });
            }else {
              $scope.driver.home = { point:{} };
            }
          });
        }else{
          $scope.driver = { home: { point:{} } };
        }
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