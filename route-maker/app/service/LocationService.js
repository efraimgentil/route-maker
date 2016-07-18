angular.module(moduleName).service("LocationService" , [
  "LocationResourceUrl", "$resource" , function( LocationResourceUrl , $resource ){
    var self = this;
    var Locations = $resource( LocationResourceUrl , { id : "@id" } , { update : { method : "PUT"} });

    self.get = function (id, callback) {
      Locations.get({id: id}, function success(data) {
        callback(data);
      });
    }

    self.query = function (filter, callback) {
      Locations.query(filter, function success(data) {
        callback(data);
      });
    }

    self.save = function (model, callback) {
      if (model.id) {
        Locations.update(model , function(data){ callback(data); });
      } else {
        Locations.save( model, function(data){ callback(data); } );
      }
    }

  }
]);