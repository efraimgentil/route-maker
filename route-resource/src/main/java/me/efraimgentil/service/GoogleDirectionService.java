package me.efraimgentil.service;

import com.fasterxml.jackson.databind.JsonNode;
import me.efraimgentil.config.SpringConfig;
import me.efraimgentil.model.Location;
import me.efraimgentil.model.Point;
import me.efraimgentil.model.Route;
import me.efraimgentil.model.Stop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 25/07/16.
 */
@Service
@Lazy(true)
public class GoogleDirectionService {

  private final String googleDirections = "https://maps.googleapis.com/maps/api/directions/json";
  @Autowired @Qualifier(SpringConfig.GOOGLE_KEY) String googleApiKey;

  protected JsonNode callGoogleService(  Route route ){
    String uri = mountApiUri( route  );
    JsonNode result = new RestTemplate().getForObject( uri , JsonNode.class);
    return validateResultAndReturn(result);
  }

  protected JsonNode validateResultAndReturn(JsonNode result) {
    String status = result.get("status").asText();
    if("NOT_FOUND".equals(status)){
    }
    return result.get("routes").get(0);
  }

  protected String mountApiUri(  Route route  ){
    String uri = googleDirections;
    uri += "?key="  + googleApiKey;
    uri += "&origin="  + route.getStartingLocation().getPoint().getLatLng();
    if(route.getEndingLocation() == null || route.getEndingLocation().getPoint() == null  ){
      uri += "&destination=" + route.getStartingLocation().getPoint().getLatLng();
    }else {
      uri += "&destination=" + route.getEndingLocation().getPoint().getLatLng();
    }
    if(route.getStops() != null && !route.getStops().isEmpty()) {
      String waipoints = "optimize:true";
      for (Stop stop : route.getStops()) {
        waipoints += "|" + stop.getPoint().getLatLng();
      }
      uri += "&waypoints=" + waipoints;
    }
    return uri;
  }

  protected List<Stop> orderStops(JsonNode routeNode, List<Stop> stops) {
    final List<Stop> orderedStops = new ArrayList<>();
    //if( !routeNode.has("waypoint_order") ) throw new NoWayPointOrderException();
    for (JsonNode n : routeNode.get("waypoint_order")) {
      orderedStops.add(stops.get(n.asInt()));
    }
    return orderedStops;
  }

}
