angular.module(moduleName).factory("MapService", function () {

  var mapService = {};
  mapService.map = null;
  var currentUserPosition = null;
  var markers = [];
  var listeners = [];
  var geocoder = new google.maps.Geocoder();
  var directionService = new google.maps.DirectionsService;
  var directionsDisplay = new google.maps.DirectionsRenderer;

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

  mapService.initializeMap = function (targetElement, lat, lng, mapConf , callback ) {
    setMapConfig(targetElement, mapConf);
    if (!lat && !lng && navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function (position) {
        currentUserPosition = position;
        mapService.map = loadMap(targetElement, position.coords.latitude, position.coords.longitude);
        google.maps.event.addListenerOnce( mapService.map  , 'tilesloaded' , function(){
          if(callback) callback();
        });
        directionsDisplay.setMap(mapService.map);
        for (var i in listeners) {
          for (var k in listeners[i]) {
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
  };

  mapService.findPlaceByName = function (address, callback) {
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
      map: mapService.map,
      title: title
    });
    mapService.map.setCenter(location);
    if (removeMarkers) mapService.removeAllMarkers();
    markers.push(marker);
  };

  mapService.addClickEvent = function (callback) {
    if (callback) {
      if (mapService.map) {
        mapService.map.addListener("click", callback);
      } else {
        listeners.push({"click": callback});
      }
    }
  }

  mapService.removeAllMarkers = function () {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
  }

  mapService.drawRoute = function (startPoint, endPoint) {
    directionService.route(
      {
        origin: startPoint,
        destination: endPoint,
        travelMode: google.maps.TravelMode.DRIVING,
      }, function (response, status) {
        if (status === google.maps.DirectionsStatus.OK) {
          console.log(response);
          directionsDisplay.setDirections(response);
        }
      });
  }

  /*mapService.gerMarksers = function(){
   return jQuery.extend(true , {} , markers );
   }*/
  return mapService;
});