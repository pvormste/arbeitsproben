library guess_a_route;

import 'dart:html';
import 'dart:async';
import 'package:google_maps/google_maps.dart';
import 'shapes.dart';

class GuessARoute {
  // General map inforation
  MapOptions _mapOptions;
  GMap _map;
  final int _zoom = 8;

  // Shapes
  Shapes _shapes;

  // Direction
  DirectionsService directionsService;
  DirectionsRenderer directionsDisplay;

  // Markers
  Marker _startMarker;
  String _startMarkerIcon = "images/blue/letter_s.png";

  Marker _guessMarker;
  String _guessMarkerIcon = "images/red/letter_e.png";

  // Output
  Node _outputField;
  void set outputField(Node of) {
    _outputField = of;
  }

  GuessARoute(String mapCanvas/*, LatLng location*/) {
    _initMap(mapCanvas, /*location,*/ true);
  }

  Future _initMap(String mapCanvas, /*LatLng location,*/ [bool isFirstInit = false]) async {
    if(isFirstInit) {
      // Set Map options
      _mapOptions = new MapOptions()
        ..zoom = _zoom
        //..center = location
        ..mapTypeId = MapTypeId.ROADMAP;

      // Create map
      _map = new GMap(querySelector(mapCanvas), _mapOptions);
      _map.onClick.listen((e) => _changeMarker(e.latLng));

      // Create Polygons for generation
      _shapes = new Shapes(_map);

      // Create direction service
      directionsService = new DirectionsService();
      directionsDisplay = new DirectionsRenderer();
      directionsDisplay.map = _map;
    }
    else {
      _map
        ..zoom = _zoom;
        //..center = location;
    }

    // Get random position
    //LatLng location = new LatLng(33.749524, -108.082944);
    await _shapes.getRandomPosition();
    _map.center = _shapes.randLocation;

    // Add start location
    _addMarker(_shapes.randLocation, true);
  }

  void _generateStartPoint() {

  }

  // Add a marker to the map. Blue for start, red for guess
  void _addMarker(LatLng location, [bool isStartMarker = false]) {
    Marker marker = new Marker(new MarkerOptions()
      ..position = location
      ..map = _map
    );

    // Red marker (guess)
    if(!isStartMarker) {
      marker.icon = _guessMarkerIcon;
      _guessMarker = marker;
    }
    // Blue marker (start)
    else {
      marker.icon = _startMarkerIcon;
      _startMarker = marker;
    }
  }

  // Change the guess marker on click
  void _changeMarker(LatLng location) {
    if(_guessMarker != null)
      _guessMarker.map = null;

    _addMarker(location);
  }

  // Calculate route between markers
  void calcRoute()
  {
    DirectionsRequest request = new DirectionsRequest()
      ..origin = _startMarker.position
      ..destination = _guessMarker.position
      ..travelMode = TravelMode.DRIVING;

    // Look for route
    directionsService.route(request, (response, status) {
      if(status == DirectionsStatus.OK) {
        directionsDisplay.directions = response;

        // Now get the distance
        DistanceMatrixRequest distanceMatrixRequest = new DistanceMatrixRequest()
          ..origins = [_startMarker.position]
          ..destinations = [_guessMarker.position]
          ..travelMode = TravelMode.DRIVING
          ..unitSystem = UnitSystem.METRIC;

        DistanceMatrixService distanceMatrixService = new DistanceMatrixService();
        distanceMatrixService.getDistanceMatrix(distanceMatrixRequest, (response, status) {
          if(status == DistanceMatrixStatus.OK) {
            writeDistance(response.rows.first.elements.first.distance.text);
          }
          else {
            print("Error in distance matrix");
          }
        });
      }
      else {
        print("Error in directions matrix");
      }
    });
  }

  void writeDistance(String txt) {
    _outputField.text = txt;
  }

}