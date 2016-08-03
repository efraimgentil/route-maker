angular.module( moduleName ).controller("TestController" , [
  "$scope" , "MapService",
  function($scope,  MapService ){
    var self = this;

    self.addMarker = function( data ){
      if(data){
        MapService.removeAllMarkers();
        MapService.addMarker( data.formatted_address  ,  $scope.stop.point );
      }
    };

    $scope.init = function(){
      $scope.stop = {  point: {}  };
      MapService.initializeMap( $("#map")[0] , null , null , { height : 300 } , function(){
        MapService.addClickEvent( function(data){
          $scope.lat = data.latLng.lat();
          $scope.lng = data.latLng.lng();
          MapService.addMarker( "Place" ,  { lat : $scope.lat , lng : $scope.lng } );
          $scope.$apply();
        });
      } );
    };

    $scope.drawRoute = function(json ){
      //var t = {"geocoded_waypoints":[{"geocoder_status":"OK","place_id":"Ek1Bdi4gQWd1YW5hbWJpLCAxMzUzLTEzOTMgLSBKb3PDqSBCb25pZmFjaW8sIEZvcnRhbGV6YSAtIENFLCA2MDA1NS00MDAsIEJyYXNpbA","types":["route","street_address"]},{"geocoder_status":"OK","place_id":"EkBBdi4gQWd1YW5hbWJpLCAyNjQtMzMyIC0gSm9zw6kgQm9uaWZhY2lvLCBGb3J0YWxlemEgLSBDRSwgQnJhc2ls","types":["route","street_address"]}],"routes":[{"bounds":{"northeast":{"lat":-3.7384161,"lng":-38.5238004},"southwest":{"lat":-3.7488277,"lng":-38.5270073}},"copyrights":"Map data ©2016 Google","legs":[{"distance":{"text":"1.6 km","value":1554},"duration":{"text":"5 mins","value":320},"end_address":"Av. Aguanambi, 264-332 - José Bonifacio, Fortaleza - CE, Brazil","end_location":{"lat":-3.7392782,"lng":-38.5247877},"start_address":"Av. Aguanambi, 1353-1393 - José Bonifacio, Fortaleza - CE, 60055-400, Brazil","start_location":{"lat":-3.7488277,"lng":-38.5238004},"steps":[{"distance":{"text":"0.3 km","value":277},"duration":{"text":"1 min","value":40},"end_location":{"lat":-3.7465872,"lng":-38.5248822},"html_instructions":"Head <b>north</b> on <b>Av. Aguanambi</b> toward <b>Rua Coronel Pergentino Ferreira</b>","polyline":{"points":"de{UvdcjFYHKBWLKBC@oDtAIDsAd@cBr@"},"start_location":{"lat":-3.7488277,"lng":-38.5238004},"travel_mode":"DRIVING"},{"distance":{"text":"8 m","value":8},"duration":{"text":"1 min","value":1},"end_location":{"lat":-3.7466232,"lng":-38.5249443},"html_instructions":"Turn <b>left</b> toward <b>Av. Aguanambi</b>","maneuver":"turn-left","polyline":{"points":"dwzUnkcjFDJ"},"start_location":{"lat":-3.7465872,"lng":-38.5248822},"travel_mode":"DRIVING"},{"distance":{"text":"0.4 km","value":406},"duration":{"text":"1 min","value":45},"end_location":{"lat":-3.7430705,"lng":-38.5256914},"html_instructions":"Turn <b>right</b> onto <b>Av. Aguanambi</b>","maneuver":"turn-right","polyline":{"points":"jwzUzkcjF]Nc@P_@JWFq@Jc@DQ@sALaAHcE\\gAJaAH"},"start_location":{"lat":-3.7466232,"lng":-38.5249443},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":132},"duration":{"text":"1 min","value":44},"end_location":{"lat":-3.7429799,"lng":-38.5267777},"html_instructions":"Turn <b>left</b> onto <b>Rua Coronel Sólon</b>","maneuver":"turn-left","polyline":{"points":"dazUppcjFc@ZA@A@?@A@?@VrD"},"start_location":{"lat":-3.7430705,"lng":-38.5256914},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":138},"duration":{"text":"1 min","value":32},"end_location":{"lat":-3.7417574,"lng":-38.5270073},"html_instructions":"Turn <b>right</b> at the 1st cross street onto <b>Rua Dom Sebastião Leme</b>","maneuver":"turn-right","polyline":{"points":"r`zUjwcjFqCZaBP"},"start_location":{"lat":-3.7429799,"lng":-38.5267777},"travel_mode":"DRIVING"},{"distance":{"text":"19 m","value":19},"duration":{"text":"1 min","value":5},"end_location":{"lat":-3.7417294,"lng":-38.5268364},"html_instructions":"Turn <b>right</b> onto <b>Rua Mestre Rosa</b>","maneuver":"turn-right","polyline":{"points":"~xyUxxcjFCQAO"},"start_location":{"lat":-3.7417574,"lng":-38.5270073},"travel_mode":"DRIVING"},{"distance":{"text":"0.4 km","value":412},"duration":{"text":"2 mins","value":91},"end_location":{"lat":-3.7384161,"lng":-38.5254173},"html_instructions":"Turn <b>left</b> onto <b>R. Eusébio de Souza</b>","maneuver":"turn-left","polyline":{"points":"xxyUvwcjFYIYGmE_AsD{@ICIEIEGGGGEICKCI?AGQQKOGSG]IQCu@O"},"start_location":{"lat":-3.7417294,"lng":-38.5268364},"travel_mode":"DRIVING"},{"distance":{"text":"92 m","value":92},"duration":{"text":"1 min","value":42},"end_location":{"lat":-3.7386732,"lng":-38.5246287},"html_instructions":"Turn <b>right</b> onto <b>Av. Domingos Olímpio</b>","maneuver":"turn-right","polyline":{"points":"bdyUzncjFd@uBJg@"},"start_location":{"lat":-3.7384161,"lng":-38.5254173},"travel_mode":"DRIVING"},{"distance":{"text":"70 m","value":70},"duration":{"text":"1 min","value":20},"end_location":{"lat":-3.7392782,"lng":-38.5247877},"html_instructions":"Turn <b>right</b> onto <b>Av. Aguanambi</b> (signs for <b>BR-116</b>/<b>Aeroporto Internacional</b>/<b>Arena Castelão</b>)<div style=\"font-size:0.9em\">Destination will be on the right</div>","maneuver":"turn-right","polyline":{"points":"teyU|icjFH@lAR`@H"},"start_location":{"lat":-3.7386732,"lng":-38.5246287},"travel_mode":"DRIVING"}],"traffic_speed_entry":[],"via_waypoint":[]}],"overview_polyline":{"points":"de{UvdcjFiA^qGbCcBr@DJaA`@w@RuAPkJv@iCTe@\\CFVrDqCZaBPCQAOYIgFgA}D_ASKOOIUCKGQQKc@Oo@Mu@Od@uBJg@H@nB\\"},"summary":"R. Eusébio de Souza and Av. Aguanambi","warnings":[],"waypoint_order":[]}],"status":"OK"};
      var t = {"geocoded_waypoints":[{"geocoder_status":"OK","place_id":"Ek1Bdi4gQWd1YW5hbWJpLCAxMzUzLTEzOTMgLSBKb3PDqSBCb25pZmFjaW8sIEZvcnRhbGV6YSAtIENFLCA2MDA1NS00MDAsIEJyYXNpbA","types":["route","street_address"]},{"geocoder_status":"OK","place_id":"ChIJ86Q67hxJxwcRGZ5ABwsYWY4","types":["street_address"]},{"geocoder_status":"OK","place_id":"EkBBdi4gQWd1YW5hbWJpLCAyNjQtMzMyIC0gSm9zw6kgQm9uaWZhY2lvLCBGb3J0YWxlemEgLSBDRSwgQnJhc2ls","types":["route","street_address"]}],"routes":[{"bounds":{"northeast":{"lat":-3.7378222,"lng":-38.5238004},"southwest":{"lat":-3.7488277,"lng":-38.53160310000001}},"copyrights":"Map data ©2016 Google","legs":[{"distance":{"text":"1.6 km","value":1593},"duration":{"text":"5 mins","value":311},"end_address":"Rua Padre Minguelino, 835 - Fatima, Fortaleza - CE, 60040-300, Brazil","end_location":{"lat":-3.743704299999999,"lng":-38.530451},"start_address":"Av. Aguanambi, 1353-1393 - José Bonifacio, Fortaleza - CE, 60055-400, Brazil","start_location":{"lat":-3.7488277,"lng":-38.5238004},"steps":[{"distance":{"text":"0.3 km","value":277},"duration":{"text":"1 min","value":40},"end_location":{"lat":-3.7465872,"lng":-38.5248822},"html_instructions":"Head <b>north</b> on <b>Av. Aguanambi</b> toward <b>Rua Coronel Pergentino Ferreira</b>","polyline":{"points":"de{UvdcjFYHKBWLKBC@oDtAIDsAd@cBr@"},"start_location":{"lat":-3.7488277,"lng":-38.5238004},"travel_mode":"DRIVING"},{"distance":{"text":"8 m","value":8},"duration":{"text":"1 min","value":1},"end_location":{"lat":-3.7466232,"lng":-38.5249443},"html_instructions":"Turn <b>left</b> toward <b>Av. Aguanambi</b>","maneuver":"turn-left","polyline":{"points":"dwzUnkcjFDJ"},"start_location":{"lat":-3.7465872,"lng":-38.5248822},"travel_mode":"DRIVING"},{"distance":{"text":"0.4 km","value":406},"duration":{"text":"1 min","value":45},"end_location":{"lat":-3.7430705,"lng":-38.5256914},"html_instructions":"Turn <b>right</b> onto <b>Av. Aguanambi</b>","maneuver":"turn-right","polyline":{"points":"jwzUzkcjF]Nc@P_@JWFq@Jc@DQ@sALaAHcE\\gAJaAH"},"start_location":{"lat":-3.7466232,"lng":-38.5249443},"travel_mode":"DRIVING"},{"distance":{"text":"0.3 km","value":341},"duration":{"text":"1 min","value":75},"end_location":{"lat":-3.743153,"lng":-38.5286472},"html_instructions":"Turn <b>left</b> onto <b>Rua Coronel Sólon</b>","maneuver":"turn-left","polyline":{"points":"dazUppcjFc@ZA@A@?@A@?@VrDPvDFlABl@B`A"},"start_location":{"lat":-3.7430705,"lng":-38.5256914},"travel_mode":"DRIVING"},{"distance":{"text":"0.3 km","value":305},"duration":{"text":"1 min","value":81},"end_location":{"lat":-3.7423013,"lng":-38.53126},"html_instructions":"Continue onto <b>R. Saldanha Marinho</b>","polyline":{"points":"tazU`cdjFWfAi@dCENe@nBEPu@hD"},"start_location":{"lat":-3.743153,"lng":-38.5286472},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":122},"duration":{"text":"1 min","value":28},"end_location":{"lat":-3.7433392,"lng":-38.53160310000001},"html_instructions":"Turn <b>left</b> onto <b>R. Assunção</b>","maneuver":"turn-left","polyline":{"points":"j|yUjsdjFzA\\dB`@LB"},"start_location":{"lat":-3.7423013,"lng":-38.53126},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":134},"duration":{"text":"1 min","value":41},"end_location":{"lat":-3.743704299999999,"lng":-38.530451},"html_instructions":"Turn <b>left</b> at the 1st cross street onto <b>Rua Padre Minguelino</b><div style=\"font-size:0.9em\">Destination will be on the left</div>","maneuver":"turn-left","polyline":{"points":"zbzUnudjFDQh@gCBMR}@"},"start_location":{"lat":-3.7433392,"lng":-38.53160310000001},"travel_mode":"DRIVING"}],"traffic_speed_entry":[],"via_waypoint":[]},{"distance":{"text":"1.2 km","value":1239},"duration":{"text":"4 mins","value":268},"end_address":"Av. Aguanambi, 264-332 - José Bonifacio, Fortaleza - CE, Brazil","end_location":{"lat":-3.7392782,"lng":-38.5247877},"start_address":"Rua Padre Minguelino, 835 - Fatima, Fortaleza - CE, 60040-300, Brazil","start_location":{"lat":-3.743704299999999,"lng":-38.530451},"steps":[{"distance":{"text":"0.1 km","value":128},"duration":{"text":"1 min","value":41},"end_location":{"lat":-3.744078099999999,"lng":-38.52935739999999},"html_instructions":"Head <b>east</b> on <b>Rua Padre Minguelino</b> toward <b>R. Barão de Aratanha</b>","polyline":{"points":"bezUhndjFLi@Lg@n@gC"},"start_location":{"lat":-3.743704299999999,"lng":-38.530451},"travel_mode":"DRIVING"},{"distance":{"text":"0.7 km","value":734},"duration":{"text":"2 mins","value":128},"end_location":{"lat":-3.7378222,"lng":-38.5272608},"html_instructions":"Turn <b>left</b> at the 2nd cross street onto <b>R. Jaime Benévolo</b>","maneuver":"turn-left","polyline":{"points":"ngzUngdjFqEeAuCq@y@QKCgEaAiEeAMEoDy@{EiAIC"},"start_location":{"lat":-3.744078099999999,"lng":-38.52935739999999},"travel_mode":"DRIVING"},{"distance":{"text":"0.3 km","value":307},"duration":{"text":"1 min","value":79},"end_location":{"lat":-3.7386732,"lng":-38.5246287},"html_instructions":"Turn <b>right</b> onto <b>Av. Domingos Olímpio</b>","maneuver":"turn-right","polyline":{"points":"j`yUjzcjFZwAXsA`@gB^{Ad@uBJg@"},"start_location":{"lat":-3.7378222,"lng":-38.5272608},"travel_mode":"DRIVING"},{"distance":{"text":"70 m","value":70},"duration":{"text":"1 min","value":20},"end_location":{"lat":-3.7392782,"lng":-38.5247877},"html_instructions":"Turn <b>right</b> onto <b>Av. Aguanambi</b> (signs for <b>BR-116</b>/<b>Aeroporto Internacional</b>/<b>Arena Castelão</b>)<div style=\"font-size:0.9em\">Destination will be on the right</div>","maneuver":"turn-right","polyline":{"points":"teyU|icjFH@lAR`@H"},"start_location":{"lat":-3.7386732,"lng":-38.5246287},"travel_mode":"DRIVING"}],"traffic_speed_entry":[],"via_waypoint":[]}],"overview_polyline":{"points":"de{UvdcjFiA^qGbCcBr@DJaA`@w@RuAPkJv@iCTe@\\CFVrDPvDJzBB`AWfAo@tCk@`Cu@hDzA\\rBd@n@yCr@}Cn@gCqEeAoEcAkLqCkKcCICZwAz@{DdAqEJg@H@nB\\"},"summary":"Av. Aguanambi","warnings":[],"waypoint_order":[0]}],"status":"OK"};
      parseJsonToReadableRoute( t.routes[0] );
      var route = t.routes[0];
      route.bounds = new google.maps.LatLngBounds( t.routes[0].bounds["northeast"] ,  t.routes[0].bounds["southwest"]  );
      var path = new google.maps.MVCArray();
      var poly = new google.maps.Polyline({ map: MapService.map , strokeColor: '#000000',
        strokeOpacity: 1.0,
        strokeWeight: 4 });
      poly.setPath(path);
      console.log( route.legs );
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
      function drawSteps( steps ){
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
                console.log("OK");
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
      console.log(steps );
      drawSteps( steps );
    }

    $scope.searchLocation = function( searchLoc ){
      MapService.findPlaceByName( searchLoc  , function(data){
        var geom = data.geometry;
        $scope.stop.point.lat = geom.location.lat();
        $scope.stop.point.lng = geom.location.lng();
        $scope.stop.pointName = $scope.searchLoc =  data.formatted_address;
        self.addMarker(data);
        $scope.$apply();
      });
    };

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