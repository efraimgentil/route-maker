angular.module( moduleName ).controller("RouteFormController" , [
  "$scope" , "MapService" , "$location",
  function($scope,  MapService , $location ){
    var self = this;
    $scope.state = "formRoute";
    self.locationVarName= "";

    $scope.init = function(){
      $scope.route = { startingLocation: {} , endingLocation: { } };
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

    $scope.cancelNewLocation = function(){
      $scope.state = "formRoute";
    }

    $scope.backToRoute = function(location){
      $scope.route[self.locationVarName] = location;
      $scope.cancelNewLocation();
    }

    $scope.copyStartingLocation = function(){
      $scope.route.endingLocation = $scope.route.startingLocation;
    }

    $scope.prepareSelectDriver = function(){
      $scope.state = "selectDriver";
    }

    $scope.selectDriverAndBackToRoute = function(driver){
      $scope.route.driver = driver;
      $scope.cancelNewLocation();
    }

  }
]);