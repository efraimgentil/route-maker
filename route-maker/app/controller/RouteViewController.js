angular.module(moduleName).controller("RouteViewController", [
  "$scope", "RouteService", "MapFactory", "$routeParams", "$location",
  function ($scope, RouteService, MapFactory, $routeParams , $location) {
    var self = this;
    self.mapService = null;

    $scope.init = function () {
      self.mapService = MapFactory.initializeMap($("#map")[0], null, null, {height: 300}, function () {
        RouteService.get($routeParams.id).$promise.then(function (data) {
          data.date = new Date(data.date);
          $scope.route = data;
          RouteService.drawRoute($scope.route, self.mapService);
        });
      });
    };

    $scope.cancel = function(){
      $location.path("/route");
    };
  }
]);