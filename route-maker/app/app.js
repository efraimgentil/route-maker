var moduleName = "rm";
var app = angular.module( moduleName , ["ngResource", "ngRoute" ]);
app.constant("$authorizationResourceUrl", "http://localhost:8080/ws");

app.config( function( $routeProvider , $locationProvider) {

  $routeProvider
    .when('/' , {
      templateUrl: 'app/view/home.html',
    })
    .when('/driver', {
      templateUrl: 'app/view/driver/list.html',
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