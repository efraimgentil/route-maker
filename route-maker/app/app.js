var moduleName = "rm";
var app = angular.module( moduleName , ["ngResource", "ngRoute" ]);
app.constant("$authorizationResourceUrl", "http://localhost:8080/ws");
app.constant("DriverResourceUrl", "http://localhost:8080/:id");


app.config( function( $routeProvider , $locationProvider) {

  $routeProvider
    .when('/' , {
      templateUrl: 'app/view/home.html',
    })
    .when('/driver', {
      templateUrl: 'app/view/driver/list.html',
      controller: "DriverListController"
    })
    .when('/driver/new', {
      templateUrl: 'app/view/driver/form.html',
      controller: "DriverFormController"
    })
    .when('/driver/:id', {
      templateUrl: 'app/view/driver/form.html',
      controller: "DriverFormController"
    })
    .when('/route', {
      templateUrl: 'app/view/route/list.html',
      controller: "RouteListController"
    })
    .when('/route/new', {
      templateUrl: 'app/view/route/form.html',
      controller: "RouteFormController"
    })
    .when('/location/new', {
      templateUrl: 'app/view/location/form.html',
      controller: "LocationFormController"
    })
    .when('/user', {
      templateUrl: 'app/view/user/user-list.html',
    })
    .otherwise({ //Anything that is not mapped will be considered home
      templateUrl: 'app/view/resource-not-found.html'
    });

  $locationProvider.html5Mode({
    enabled: false,
    requireBase: false
  }).hashPrefix('!');
});