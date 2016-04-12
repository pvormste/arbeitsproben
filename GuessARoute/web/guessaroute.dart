import 'dart:html';
import 'package:guess_a_route/guess_a_route.dart';
import 'package:google_maps/google_maps.dart';

GuessARoute guessARoute;

void main() {
  guessARoute = new GuessARoute('#map-canvas'/*, new LatLng(33.749524, -108.082944)*/)
    ..outputField = querySelector('#distance-output');

  var calcRouteButton = querySelector('#calc-route');
  calcRouteButton.onClick.listen((e) => guessARoute.calcRoute());
}