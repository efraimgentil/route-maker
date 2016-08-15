angular.module(moduleName).service("RouteService" , [
  "$resource" , "$http" , "RouteResourceUrl", function( $resource , $http , RouteResourceUrl ){
    var self = this;
    var Routes = $resource( RouteResourceUrl  +"/:id ", { id: "@id"} , { update: {method : "PUT" } });

    self.suggestDriver = function( route , callback ){
      $http.post( RouteResourceUrl+ "/suggest-driver" , route ).then( callback );
    };

    self.get = function (id, callback) {
      Routes.get({id: id}, function success(data) {
        callback(data);
      });
    }

    self.query = function (filter, callback) {
      Routes.query(filter, function success(data) {
        callback(data);
      });
    }

    self.save = function (model, callback) {
      if (model.id) {
        Routes.update(model , callback );
      } else {
        Routes.save( model,  callback(data) );
      }
    }


  }
]);