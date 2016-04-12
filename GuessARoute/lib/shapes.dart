library guess_a_route.shapes;

import 'dart:math';
import 'dart:async';
import 'package:google_maps/google_maps.dart';
import 'package:google_maps/google_maps_geometry.dart';

enum Region {
  NORTH_AMERICA
}

class Shapes {
  // Geocoder
  Geocoder geocoder;
  GeocoderRequest geocoderRequest;

  // Random Location
  LatLng randLocation;

  // USA + Canada
  var _northAmericaCoords = <LatLng>[
      new LatLng(49.282426, -123.107505), // Vancouver
      new LatLng(37.760109, -122.345674), // San Francsico
      new LatLng(34.613725, -120.407232), // Santa Barbara
      new LatLng(24.807613, -112.224980), // Comondu (Baja California)
      new LatLng(17.324943, -101.073276), // Guerrero
      new LatLng(13.705948, -89.998496), // El Salvador
      new LatLng(21.182419, -86.816641), // Cancun
      new LatLng(21.273443, -97.447471), // Estero La Cinega
      new LatLng(27.759263, -97.381440), // Corpus Christi
      new LatLng(29.911570, -90.071403), // New Orleans
      new LatLng(27.888801, -82.699028), // Tampa
      new LatLng(26.165529, -81.759332), // Central Naples
      new LatLng(25.668965, -80.157347), // Miami
      new LatLng(32.856448, -79.778728), // Charleston
      new LatLng(35.564597, -75.468365), // Outer Banks
      new LatLng(39.374136, -74.431643), // Atlantic City
      new LatLng(39.374136, -74.431643), // Halifax
      new LatLng(46.809655, -71.257044), // Vanier
      new LatLng(49.931182, -97.126050), // Winnipeg
      new LatLng(49.282426, -123.107505) // Vancouver
  ];

  Polygon _northAmeria;
  //Polygon get northAmeria => _northAmeria;
  LatLngBounds _northAmericaBounds;

  Shapes(GMap map) {
    // Init Geocoder
    geocoder = new Geocoder();
    geocoderRequest = new GeocoderRequest();

    _northAmeria = _createPolygon(map, _northAmericaCoords);
    _northAmericaBounds = _createRectangle(_northAmeria);
  }

  Polygon _createPolygon(GMap map, List<LatLng> coords) {
    PolygonOptions polyOptions = new PolygonOptions()
      ..paths = coords
      ..strokeColor = '#FF0000'
      ..strokeOpacity = 0.8
      ..strokeWeight = 3
      ..fillColor = '#FF0000'
      ..fillOpacity = 0.35;

    return new Polygon(polyOptions)
      ..map = map;
  }

  LatLngBounds _createRectangle(Polygon polygon) {
    LatLngBounds bounds = new LatLngBounds();

    for(int i = 0; i < polygon.path.length; ++i) {
      bounds.extend(polygon.path.getAt(i));
    }

    return bounds;
  }

  Future getRandomPosition() async {
    Random rnd = new Random();
    int rndNum = rnd.nextInt(100) % 1;

    LatLng sw = null;
    LatLng ne = null;
    Polygon searchPoly = null;

    switch(rndNum) {
      case 0: // North America
        sw = _northAmericaBounds.southWest;
        ne = _northAmericaBounds.northEast;
        searchPoly = _northAmeria;
        break;
    }

    print(sw.toString());
    print(ne.toString());

    // Now calculate a random position
    await _checkRandomPosition(sw, ne);
    /*bool searchingForLocation = true;
    LatLng randLocation = null;
    Geocoder geocoder = new Geocoder();
    GeocoderRequest geocoderRequest = new GeocoderRequest();
    int i = 0;
    while(searchingForLocation) {

      randLocation = new LatLng(rnd.nextDouble() * (ne.lat - sw.lat) + sw.lat, rnd.nextDouble() * (ne.lng - sw.lng) + sw.lng);
      print(randLocation.toString());

      //if(poly.containsLocation(randLocation, searchPoly))
      //  searchingForLocation = false;

      geocoderRequest.location = randLocation;
      geocoder.geocode(geocoderRequest, (results, status) {
        if(status == GeocoderStatus.OK){
          print('Good Address!');
          print(results.first.addressComponents.first.longName);
        }

      });

      ++i;
      if(i > 15)
        searchingForLocation = false;
    }

    /*

    var ptLat = Math.random() * (ne.lat() - sw.lat()) + sw.lat();
       var ptLng = Math.random() * (ne.lng() - sw.lng()) + sw.lng();
       var point = new google.maps.LatLng(ptLat,ptLng);
       if (google.maps.geometry.poly.containsLocation(point,polygon)) {
         createMarker(map, point,"marker "+i);
     */
*/
  }

  Future _checkRandomPosition(LatLng sw, LatLng ne) async {
    Random rnd = new Random();
    randLocation = new LatLng(rnd.nextDouble() * (ne.lat - sw.lat) + sw.lat, rnd.nextDouble() * (ne.lng - sw.lng) + sw.lng);
    print("Random: "+randLocation.toString());

    geocoderRequest.location = randLocation;
    geocoder.geocode(geocoderRequest, await (results, status) async {
      if(status == GeocoderStatus.OK){
        print('Good Address!');
        print(results.first.addressComponents.first.longName);
      }
      else {
        await _checkRandomPosition(sw, ne);
      }
    });
  }
}