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

    $scope.print = function(){
      var body               = $('body');
      var mapContainer       = $('.map-container');
      var mapContainerParent = mapContainer.parent();
      var printContainer     = $('<div>');
      var fields = $('.fields');
      var form = $('#formViewRoute');

      printContainer
        .addClass('print-container')
        .css('position', 'relative')
        .append( fields )
        .height(mapContainer.height())
        .append(mapContainer)
        .prependTo(body);

      var content = body
        .children()
        .not('script')
        .not(printContainer)
        .detach();

      // Patch for some Bootstrap 3.3.x `@media print` styles. :|
      var patchedStyle = $('<style>')
        .attr('media', 'print')
        .text('img { max-width: none !important; }' +
          'a[href]:after { content: ""; }')
        .appendTo('head');

      window.print();

      body.prepend(content);
      form.prepend( fields );
      mapContainerParent.prepend(mapContainer);

      printContainer.remove();
      patchedStyle.remove();

    };

  }
]);