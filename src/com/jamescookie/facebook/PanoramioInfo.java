package com.jamescookie.facebook;

import java.awt.Point;
import java.net.URL;

public class PanoramioInfo {
    private final Point point;
    private final URL url;

    public PanoramioInfo(Point point, URL url) {
        this.point = point;
        this.url = url;
    }

    public Point getPoint() {
        return point;
    }

    public URL getUrl() {
        return url;
    }
}
