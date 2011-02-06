$(function() {
    var map,
    init = function() {
        FB.init({
            appId  : '6995620803',
            status : true, // check login status
            cookie : true, // enable cookies to allow the server to access the session
            xfbml  : true  // parse XFBML
        });

        FB.getLoginStatus(function(response) {
            if (response.session) {
                checkUser(response.session.uid);
            } else {
                $('#connecting').hide();
                $('#connect').click(connect).show();
            }
        });

        $('#setUser').live('click', function(ev) {
            ev.preventDefault();
            var button = $(this),
                form = button.parent(),
                url =  form.attr('action'),
                inputs = $(':input', form),
                dataString = "submitted=true";

            inputs.each(function() {
                dataString += '&'+this.name +'='+ $(this).val();
            });

            $.ajax({
                type: "POST",
                url: url,
                data: dataString,
                success: function(data) {
                    $('#fb-root').html(data);
                    checkMap();
                }
            });

            return false;
        });
    },

    connect = function(ev) {
        ev.preventDefault();
        FB.login(function(response) {
            if (response.session) {
                $('#connect').hide();
                $('#connecting').show();
                checkUser(response.session.uid);
            }
        });
    },

    checkUser = function(userId) {
        $('#fb-root').load($('#checkUser').attr('href')+"/"+userId, checkMap);
    },

    checkMap = function() {
        var mapContainer = $('#map');
        if (mapContainer && GBrowserIsCompatible()) {
            initMapLinks();

            map = new GMap2(mapContainer[0]);
            if (mapContainer.data('lat') && mapContainer.data('long') && mapContainer.data('zoom')) {
                map.setCenter(new GLatLng(mapContainer.data('lat'), mapContainer.data('long')), mapContainer.data('zoom'));
            } else {
                map.setCenter(new GLatLng(51.417689690776456, -0.19297689199447632), 2);
            }
            map.setMapType(G_SATELLITE_MAP);
            map.addControl(new GSmallMapControl());
            map.enableDoubleClickZoom();
            map.enableContinuousZoom();
            map.enableScrollWheelZoom();
            var panoLayer = new PanoramioLayer(map, mapContainer.data('user'));
            panoLayer.enable();
            panoLayer.load(panoLayer);
        }
    },

    initMapLinks = function() {
        $('#changeUserId').click(function(ev) {ev.preventDefault();$('#fb-root').load($('#changeUserId').attr('href'));});
        $('#changeMapLocation').click(findAndPostCurrentMapSettings);
    },

    findAndPostCurrentMapSettings = function(ev) {
        ev.preventDefault();
        var centre = map.getCenter(),
            dataString = "latitude="+centre.y+"&longitude="+centre.x+"&zoom="+map.getZoom();

        $('#changeMapLocation').append('<div id="messagepop">Please wait...</div>');
        $('#messagepop').show('fast');

        $.ajax({
            type: "POST",
            url: $(this).attr('href'),
            data: dataString,
            success: function(data) {
                var message = $('#messagepop');
                message.text('Successfully changed default map settings.');
                setTimeout(function() {message.hide('fast', function() {message.remove()})}, 4000);
            }
        });
    };

    $(init);
});
