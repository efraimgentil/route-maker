angular.module(moduleName).directive('onEnter', function () {
  return function (scope, element, attrs) {
    element.bind("keyup keypress", function (event) {
      if(event.which === 13) {
        scope.$apply(function (){
          scope.$eval(attrs.onEnter);
        });
        event.preventDefault();
      }
    });
  };
});