angular.module( moduleName ).controller("RouteFormController" , [
  "$scope" , "MapService" , "$location",
  function($scope,  MapService , $location ){
    var self = this;
    $scope.state = "formRoute";
    self.locationVarName= "";

    $scope.init = function(){
      $scope.route = { startingLocation: {} , endingLocation: { } , stops : [] };
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

    $scope.addStop = function( stop ){
      $scope.route.stops.push( stop );
      $scope.backToRouteForm();
    }

    $scope.prepareAddStop = function(){
      $scope.state = "formStop";
    }

  }
]);