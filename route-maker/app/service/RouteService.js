var m = null;
angular.module(moduleName).service("RouteService" , [
  "$resource" , "$http" , "RouteResourceUrl", function( $resource , $http , RouteResourceUrl ){
    var self = this;
    var Routes = $resource( RouteResourceUrl  +"/:id ", { id: "@id"} , { update: {method : "PUT" } });

    self.suggestDriver = function( route , callback ){
      $http.post( RouteResourceUrl+ "/suggest-driver" , route ).then( callback );
    };

    self.get = function (id, callback) {
      return Routes.get({id: id});
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
        Routes.save( model,  callback );
      }
    }

    self.mountRoute = function( route , callback ){
      $http.post( RouteResourceUrl+ "/mount-route" , route ).then( callback );
    }


    self.drawRoute = function( route , MapService ){
      var route = JSON.parse( route.routeJson );
      parseJsonToReadableRoute( route );
      var steps = [];
      for( var i = 0 ; i <  route.legs.length ; i++  ){
        var leg = route.legs[i];
        console.log( leg.steps );
        for( var j = 0 ; j < leg.steps.length ; j++  ){
          var step = leg.steps[j];
          steps.push( step );
          if( j+1 >= leg.steps.length && i+1 < route.legs.length ){
            step.isParada = true;
            step.arrKey = i ;
          }
        }
      }
      MapService.drawRouteFromSteps( steps );
    }

    function parseJsonToReadableRoute( json ){
      for( k in json ){
        var el = json[k];
        if( isObject( el ) ){
          if(  isLatLngObject( el ) ){
            json[k] = new google.maps.LatLng( el["lat"],  el["lng"] ) ;
          }else{
            parseJsonToReadableRoute(el);
          }
        }
        if( isArray( el ) ){
          for( var i = 0 ; i < el.length ; i++ ){
            parseJsonToReadableRoute( el[i] );
          }
        }
      }
    }

    function isLatLngObject( el ){
      return el["lat"] && el["lng"];
    }

    function isObject( o ){
      return Object.prototype.toString.call( o ) === "[object Object]";
    }

    function isArray( o ){
      return Object.prototype.toString.call( o ) === "[object Array]";
    }

  }
]);