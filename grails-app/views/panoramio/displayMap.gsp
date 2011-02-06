<a id="changeMapLocation" href="<g:createLink action="changeMapLocation" id="${user.facebookId}"/>">Set Default Map Display To Current</a> |
<a id="changeUserId" href="<g:createLink action="setUser" id="${user.facebookId}"/>">Set User Id</a> |
<a href="http://www.panoramio.com/">Visit Panoramio</a>
<br/><br/>
<div id="map" data-user="${user.panoramioId}" data-lat="${user.geoPreference?.latitude}" data-long="${user.geoPreference?.longitude}" data-zoom="${user.geoPreference?.zoom}">
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
