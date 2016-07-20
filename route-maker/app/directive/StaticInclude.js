angular.module( moduleName ).directive( "staticInclude", [  function(){
  return {
      restrict: 'AE'
      ,templateUrl: function(elem, attrs) { return attrs.ngIncludeSrc; }
  };
}]);