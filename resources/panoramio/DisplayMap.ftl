<html>
<head>
    <title>Display Map</title>
    <script type="text/javascript" src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=${mapKey}"></script>
    <script type="text/javascript" src="${fullURL}/markermanager.js"></script>
    <script type="text/javascript" src="${fullURL}/pano_layer.js"></script>
    <script type="text/javascript">
    //<![CDATA[
    var map;

    function setupMap() {
        if (GBrowserIsCompatible()) {
            map = new GMap2(document.getElementById("map"));
            map.setCenter(new GLatLng(51.417689690776456, -0.19297689199447632), 1);
            map.setMapType(G_SATELLITE_MAP);
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
  <div id="map" style="width:100%; height:500px; background-color:#C0C0C0;">
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