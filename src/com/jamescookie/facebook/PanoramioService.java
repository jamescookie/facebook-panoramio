package com.jamescookie.facebook;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanoramioService {
    private static Logger log = Logger.getLogger(PanoramioService.class);
    private static final int CIRCUMFERENCE = 380;
    private static Double RADIUS = CIRCUMFERENCE / (2 * Math.PI);

    public static List<PanoramioInfo> getPhotos(String userId) throws Exception {
        HashMap<String, CharSequence> params = new HashMap<String, CharSequence>();
        List<PanoramioInfo> list = new ArrayList<PanoramioInfo>();

        params.put("order", "popularity");
        params.put("set", "full");
        params.put("from", "0");
        params.put("to", "100");
        params.put("minx", "-180");
        params.put("miny", "-90");
        params.put("maxx", "180");
        params.put("maxy", "90");
        params.put("size", "thumbnail");
        params.put("user", userId);

        InputStream data = postRequest(params);
        JSONObject json = new JSONObject(InputStreamToString(data));
        JSONArray photos = (JSONArray) json.get("photos");
        for (int i = 0; i < photos.length(); i++) {
            JSONObject o = (JSONObject) photos.get(i);
            Double latitude = (Double) o.get("latitude");
            Double longitude = (Double) o.get("longitude");
            URL url = new URL((String) o.get("photo_file_url"));
            list.add(new PanoramioInfo(findXY(latitude, longitude), url));
        }

        return list;
    }

    private static Point findXY(Double latitude, Double longitude) {
        return new Point((int) Math.round(longToX(longitude)), (int) Math.round(latToY(latitude)));
    }

    private static Double longToX(Double longitudeDegrees) {
        Double longitude = Math.toRadians(longitudeDegrees);
        double x = (RADIUS * longitude);
        x += (CIRCUMFERENCE / 2.0);
        return x;
    }

    private static Double latToY(Double latitudeDegrees) {
        Double latitude = Math.toRadians(latitudeDegrees);
        Double y = RADIUS / 2.0 *
                Math.log((1.0 + Math.sin(latitude)) /
                        (1.0 - Math.sin(latitude)));
        y += (CIRCUMFERENCE / 2.0);
        y -= (CIRCUMFERENCE);
        y *= -1;
        return y;
    }

    private static String InputStreamToString(InputStream inputStream) throws IOException {
        InputStreamReader in = new InputStreamReader(inputStream);
        StringWriter write = new StringWriter();
        int blocksize = 8 * 1024;
        char buffer[] = new char[blocksize];

        while (true) {
            int l = in.read(buffer, 0, blocksize);

            if (l == -1) {
                break;
            }

            write.write(buffer, 0, l);
        }

        write.close();
        inputStream.close();

        return write.toString();
    }

    private static InputStream postRequest(Map<String, CharSequence> params) throws IOException {
        CharSequence buffer = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL("http://www.panoramio.com/map/get_panoramas.php?" + buffer);

        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            log.error("Couldn't find GET protocol??", e);
        }
        conn.setDoOutput(true);
        conn.connect();

        return conn.getInputStream();
    }

    private static CharSequence delimit(Collection<Map.Entry<String, CharSequence>> entries,
                                        CharSequence delimiter, CharSequence equals,
                                        boolean doEncode) {
        if (entries == null || entries.isEmpty())
            return null;

        StringBuilder buffer = new StringBuilder();
        boolean notFirst = false;
        for (Map.Entry<String, CharSequence> entry : entries) {
            if (notFirst)
                buffer.append(delimiter);
            else
                notFirst = true;
            CharSequence value = entry.getValue();
            buffer.append(entry.getKey()).append(equals).append(doEncode ? encode(value) : value);
        }
        return buffer;
    }

    private static String encode(CharSequence target) {
        String result = target.toString();
        try {
            result = URLEncoder.encode(result, "UTF8");
        } catch (UnsupportedEncodingException e) {
            log.error(String.format("Unsuccessful attempt to encode '%s' into UTF8", result), e);
        }
        return result;
    }
}
