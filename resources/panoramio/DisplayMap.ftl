<html>
<head>
    <title>Display Map</title>
    <script type="text/javascript" src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=${mapKey}"></script>
    <script type="text/javascript" src="http://jamescookie.com/facebook/panoramio/markermanager.js"></script>
    <script type="text/javascript" src="http://jamescookie.com/facebook/panoramio/pano_layer.js"></script>
    <script type="text/javascript">
    //<![CDATA[
    var map;

    function setupMap() {
        if (GBrowserIsCompatible()) {
            map = new GMap2(document.getElementById("map"));
            map.setCenter(new GLatLng(51.417689690776456, -0.19297689199447632), 1);
            map.setMapType(G_SATELLITE_MAP);
            map.addControl(new GMapTypeControl());
            map.addControl(new GSmallMapControl());
            map.enableDoubleClickZoom();
            map.enableContinuousZoom();
            map.enableScrollWheelZoom();
            var panoLayer = new PanoramioLayer(map, ${userId});
            panoLayer.enable();
        }
    }

    //]]>
    </script>
</head>
<body onload="setupMap()" onunload="GUnload()">
  <p>
      Display Map |
      <a href="<@s.url action='UserId' method='change'/>">Set User Id</a> |
      <a href="http://www.panoramio.com/">Visit Panoramio</a>
  </p>
  <div id="map" style="width:80%; height:500px; margin-left:10%; background-color:#C0C0C0">
    <noscript>
      <div>
        <b>JavaScript must be enabled in order for you to use Google Maps.</b><br/>
        However, it seems JavaScript is either disabled or not supported by your browser.
        To view Google Maps, enable JavaScript by changing your browser options, and then
        try again.<br/><br/>
      </div>
    </noscript>
    <div>
      As Google Maps is not supported by your browser, you will not be able to view the map.
    </div>
  </div>
</body>
</html>