function PanoramioLayerCallback(json, panoLayer) {
    var batch = [];
    for (var i = 0; i < json.photos.length; i++) {
        var photo = json.photos[i];
        if (!panoLayer.ids[photo.photo_id]) {
            var marker = this.createMarker(photo, panoLayer.markerIcon);
            batch.push(marker);
            panoLayer.ids[photo.photo_id] = "exists";
        }
    }
    panoLayer.mgr.addMarkers(batch, 0);
    panoLayer.mgr.refresh();
}

PanoramioLayerCallback.prototype.formImgUrl = function(photoId, imgType) {
    return 'http://www.panoramio.com/photos/' + imgType + '/' + photoId + '.jpg';
}

PanoramioLayerCallback.prototype.formPageUrl = function(photoId) {
    return 'http://www.panoramio.com/photo/' + photoId;
}

PanoramioLayerCallback.prototype.createMarker = function(photo, baseIcon) {
    var markerIcon = new GIcon(baseIcon);
    markerIcon.image = this.formImgUrl(photo.photo_id, "mini_square");
    var marker = new GMarker(new GLatLng(photo.latitude, photo.longitude), {icon: markerIcon});

    if (photo.photo_title.length > 33) {
        photo.photo_title = photo.photo_title.substring(0, 33) + "&#8230;";
    }
    var html = "<div class='infowindow'>" +
               "<br /><a href='http://www.panoramio.com/' target='_blank'>" +
               "<img src='http://www.panoramio.com/img/logo-small.gif' border='0' width='119px' height='25px' alt='Panoramio logo' /><\/a><br />" +
               "<a id='photo_infowin' target='_blank' href='" + photo.photo_url + "'>" +
               "<img border='0' width='" + photo.width + "' height='" + photo.height + "' src='" + photo.photo_file_url + "'/><\/a>" +
               "<div style='overflow: hidden; width: 240px;'>" +
               "<a target='_blank' class='photo_title' href='" + photo.photo_url +
               "'><strong>" + photo.photo_title + "<\/strong><\/a>" +
               "<\/div>" +
               "<\/div>";

    marker.html = html;

    GEvent.addListener(marker, "click", function() {
        marker.openInfoWindow(marker.html);
    });

    return marker;
}


function PanoramioLayer(map, userId) {
    var me = this;
    me.ids = {};
    me.mgr = new MarkerManager(map, {maxZoom: 19});
    me.userId = userId;

    var icon = new GIcon();
    icon.image = "http://www.panoramio.com/img/panoramio-marker.png";
    icon.shadow = "";
    icon.iconSize = new GSize(24, 24);
    icon.shadowSize = new GSize(22, 22);
    icon.iconAnchor = new GPoint(9, 9);
    icon.infoWindowAnchor = new GPoint(9, 0);

    me.markerIcon = icon;
    me.enabled = false;

    GEvent.addListener(map, "moveend", function() {
        if (me.enabled) {
            var bounds = map.getBounds();
            var southWest = bounds.getSouthWest();
            var northEast = bounds.getNorthEast();
            me.load(me, {maxy: northEast.lat(), miny: southWest.lat(), maxx: northEast.lng(), minx: southWest.lng()});
        }
    });
}

PanoramioLayer.prototype.enable = function() {
    this.enabled = true;
    GEvent.trigger(map, "moveend");
}

PanoramioLayer.prototype.disable = function() {
    this.enabled = false;
    this.mgr.clearMarkers();
    this.ids = {};
}

PanoramioLayer.prototype.load = function(panoLayer, userOptions) {
    var options = {
        order: "popularity",
        set: "full",
        from: "0",
        to: "100",
        minx: "-180",
        miny: "-90",
        maxx: "180",
        maxy: "90",
        size: "thumbnail",
        user: panoLayer.userId
    };

    for (optionName in userOptions) {
        if (userOptions.hasOwnProperty(optionName)) {
            options[optionName] = userOptions[optionName];
        }
    }

    var url = "http://www.panoramio.com/map/get_panoramas.php?";
    var uniqueID = "";

    for (optionName in options) {
        if (options.hasOwnProperty(optionName)) {
            var optionVal = "" + options[optionName] + "";
            url += optionName + "=" + optionVal + "&";
            uniqueID += optionVal.replace(/[^\w]+/g, "");
        }
    }
    var callbackName = "PanoramioLayerCallback.loader" + uniqueID; //ask dion
    eval(callbackName + " = function(json) { var pa = new PanoramioLayerCallback(json, panoLayer);}");

    var script = document.createElement('script');
    script.setAttribute('src', url + 'callback=' + callbackName);
    script.setAttribute('id', 'jsonScript');
    script.setAttribute('type', 'text/javascript');
    document.documentElement.firstChild.appendChild(script);
}

