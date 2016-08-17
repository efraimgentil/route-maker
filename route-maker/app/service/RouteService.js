var m = null;
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

    self.mountRoute = function( route , callback ){
      $http.post( RouteResourceUrl+ "/mount-route" , route ).then( callback );
    }


    self.drawRoute = function( route , MapService ){

      //parseJsonToReadableRoute( t.routes[0] );
      var route = JSON.parse( route.routeJson );
      //console.log( route );
      parseJsonToReadableRoute( route );
      var path = new google.maps.MVCArray();
      var poly = new google.maps.Polyline({ map: MapService.map , strokeColor: '#000000', strokeOpacity: 1.0, strokeWeight: 4 }); poly.setPath(path);

      // console.log( route.legs );
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
      /*function drawSteps( steps ){
        if(steps.length > 0 ){
          try{
            var step =  steps.shift();
            if(path.getLength() == 0){
              path.push( step.start_location );
              MapService.addMarker( "Start Point" ,step.start_location );
            }
            MapService.getDirectionService().route({
              origin: path.getAt(path.getLength() - 1),
              destination:  step.end_location ,
              travelMode: google.maps.DirectionsTravelMode.DRIVING
            }, function(result, status) {
              if (status == google.maps.DirectionsStatus.OK) {
                for (var i = 0, len = result.routes[0].overview_path.length; i < len; i++) {
                  path.push(result.routes[0].overview_path[i]);
                }
                if(steps.length > 0) {
                  if(step.isParada){
                    MapService.addMarker( "Stop" , step.end_location  );
                  }
                  drawSteps(steps);
                }else{
                  MapService.addMarker( "Ending Point" , path.getAt( path.length - 1 ) );
                }
              }else if( status === "OVER_QUERY_LIMIT"){
                steps.unshift( step );
                setTimeout( function(){ drawSteps(steps)  }, 100 );
              }
            });
          }catch(e){
            console.log(e);
          }
        }
      }
      //console.log(steps );
      drawSteps( steps );
      m = MapService.map;*/
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