angular.module(moduleName).service("DriverService", ["$resource", "DriverResourceUrl",
  function ($resource, DriverResourceUrl) {
    var self = this;
    var Drivers = $resource(DriverResourceUrl, {id: "@id"}, { update: {method: 'PUT'} });


    self.get = function (id, callback) {
      Drivers.get({id: id}, function success(data) {
        callback(data);
      });
    }

    self.query = function (filter, callback) {
      Drivers.query(filter, function success(data) {
        callback(data);
      });
    }

    self.save = function (model, callback) {
      if (model.id) {
        Drivers.update(model , function(data){ callback(data); });
      } else {
        Drivers.save( model, function(data){ callback(data); } );
      }
    }

  }
]);