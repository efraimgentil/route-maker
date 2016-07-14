angular.module( moduleName ).directive( "pageTitleDirective", [ "$location" , "$rootScope" ,  function($location , $rootScope){
  return {
      restrict: 'E'
    , scope : {
       title : "@"
      ,newUrl : "@"
    }
    , transclude: true
    , replace: true
    , link: function( scope , element , attrs ){ }
    , templateUrl: 'app/view/directive/page-title-directive.html'
  };
}]);