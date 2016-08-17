angular.module(moduleName).factory("MapFactory", function () {

  var loadMap = function (targetElement, lat, lng) {
    return new google.maps.Map(targetElement, {
      center: {lat: lat, lng: lng},
      zoom: 14
    });
  }

  var setMapConfig = function (targetElement, config) {
    if (config) {
      if (config.height) targetElement.style.height = config.height;
    }
  }

  return {
    initializeMap : function( targetElement, lat, lng, mapConf , callback ){
      var mapService = { };
      mapService.map = null;
      mapService.currentUserPosition = null;
      mapService.markers = [];
      mapService.listeners = [];
      mapService.geocoder = new google.maps.Geocoder();
      mapService.directionService = new google.maps.DirectionsService;
      mapService.directionsDisplay = new google.maps.DirectionsRenderer;

      setMapConfig(targetElement, mapConf);
      if (!lat && !lng && navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
          mapService.currentUserPosition = position;
          mapService.map = loadMap(targetElement, position.coords.latitude, position.coords.longitude);
          google.maps.event.addListenerOnce( mapService.map  , 'tilesloaded' , function(){
            if(callback) callback();
          });
          mapService.directionsDisplay.setMap(mapService.map);
          for (var i in mapService.listeners) {
            for (var k in mapService.listeners[i]) {
              mapService.map.addListener(k, listeners[i][k]);
            }
          }
        });
      } else {
        mapService.map = loadMap(targetElement, lat, lng);
        google.maps.event.addListenerOnce( mapService.map  , 'tilesloaded' , function(){
          //console.log("WHAAAAAAAT ? ");
        });
      }


      mapService.findPlaceByName = function (address, callback) {map
        geocoder.geocode({'address': address}, function (results, status) {
          if (status == google.maps.GeocoderStatus.OK) {
            callback(results[0]);
          } else {
            callback(null);
          }
        });
      }

      mapService.findPlaceByLocation = function ( latLng, callback) {
        geocoder.geocode({'location': latLng}, function (results, status) {
          if (status == google.maps.GeocoderStatus.OK) {
            callback(results[0]);
          } else {
            callback(null);
          }
        });
      }

      mapService.addMarker = function (title, location, removeMarkers) {
        removeMarkers = removeMarkers || false;
        var marker = new google.maps.Marker({
          position: location,
          map: this.map,
          title: title
        });
        mapService.map.setCenter(location);
        if (removeMarkers) mapService.removeAllMarkers();
        this.markers.push(marker);
      };

      mapService.addClickEvent = function (callback) {
        if (callback) {
          if (this.map) {
            this.map.addListener("click", callback);
          } else {
            this.listeners.push({"click": callback});
          }
        }
      }

      mapService.removeAllMarkers = function () {
        for (var i = 0; i < this.markers.length; i++) {
          this.markers[i].setMap(null);
        }
      }

      mapService.drawRoute = function (startPoint, endPoint) {
        mapService.directionService.route(
          {
            origin: startPoint,
            destination: endPoint,
            travelMode: google.maps.TravelMode.DRIVING,
          }, function (response, status) {
            if (status === google.maps.DirectionsStatus.OK) {
              directionsDisplay.setDirections(response);
            }
          });
      }

      mapService.drawRouteFromSteps = function( steps ){
        if( mapService.poly ) {
          mapService.poly.setMap(null);
          mapService.removeAllMarkers();
        }
        mapService.path = new google.maps.MVCArray();
        mapService.poly = new google.maps.Polyline({ map: this.map , strokeColor: '#000000', strokeOpacity: 1.0, strokeWeight: 4 });
        mapService.poly.setPath(mapService.path);
        function drawSteps( steps ){
          if(steps.length > 0 ){
            try{
              var step =  steps.shift();
              if(mapService.path.getLength() == 0){
                mapService.path.push( step.start_location );
                mapService.addMarker( "Start Point" ,step.start_location );
              }
              mapService.directionService.route({
                origin: mapService.path.getAt(mapService.path.getLength() - 1),
                destination:  step.end_location ,
                travelMode: google.maps.DirectionsTravelMode.DRIVING
              }, function(result, status) {
                if (status == google.maps.DirectionsStatus.OK) {
                  for (var i = 0, len = result.routes[0].overview_path.length; i < len; i++) {
                    mapService.path.push(result.routes[0].overview_path[i]);
                  }
                  if(steps.length > 0) {
                    if(step.isParada){
                      mapService.addMarker( "Stop" , step.end_location  );
                    }
                    drawSteps(steps);
                  }else{
                    mapService.addMarker( "Ending Point" , mapService.path.getAt( mapService.path.length - 1 ) );
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

      }

      mapService.getDirectionService = function(){
        return this.directionService;
      }

      return mapService;
    }
  }
});