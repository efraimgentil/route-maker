angular.module( moduleName ).controller("RouteFormController" , [
  "$scope" , "RouteService" , "MapFactory", "$routeParams" ,
  function($scope,  RouteService  , MapFactory  , $routeParams){
    var self = this;
    $scope.state = "formRoute";
    self.locationVarName= "";
    self.mapService = null;

    $scope.init = function(){
      $scope.route = { startingLocation: {} , endingLocation: { } , stops : [] };
      self.mapService = MapFactory.initializeMap( $("#map")[0] , null , null , { height : 300 } , function(){ } );
    }

    $scope.new = function(){
        
    }

    $scope.newLocation = function(locationVarName){
      $scope.state = "formLocation";
      self.locationVarName = locationVarName;
    }

    $scope.prepareSelectLocation = function(locationVarName){
      $scope.state = "selectLocation";
      self.locationVarName = locationVarName;

    }

    $scope.backToRouteForm = function(){
      $scope.state = "formRoute";
    }

    $scope.backToRoute = function(location){
      $scope.route[self.locationVarName] = location;
      $scope.backToRouteForm();
    }

    $scope.copyStartingLocation = function(){
      $scope.route.endingLocation = $scope.route.startingLocation;
    }

    $scope.prepareSelectDriver = function(){
      $scope.state = "selectDriver";
    }

    $scope.selectDriverAndBackToRoute = function(driver){
      $scope.route.driver = driver;
      $scope.backToRouteForm();
    }

    $scope.suggestDriver = function(){
      RouteService.suggestDriver( $scope.route , function(response){
        $scope.route.driver = response.data;
      } );
    }

    $scope.addStop = function( stop ){
      $scope.route.stops.push( stop );
      $scope.backToRouteForm();
    }

    $scope.prepareAddStop = function(){
      $scope.state = "formStop";
    }

    $scope.previewRoute = function(){
      RouteService.mountRoute( $scope.route , function(response){
        console.log(response.data);
        RouteService.drawRoute( response.data , self.mapService );
      });
    }

  }
]);