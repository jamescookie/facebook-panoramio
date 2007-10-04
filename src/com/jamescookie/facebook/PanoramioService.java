package com.jamescookie.facebook;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

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
    public static final String ERROR_TAG = "error_response";

    public static List<URL> getPhotos(String userId) throws Exception {
        HashMap<String, CharSequence> params = new HashMap<String, CharSequence>();
        List<URL> list = new ArrayList<URL>();

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
            findXY(latitude, longitude);
            URL url = new URL((String) o.get("photo_file_url"));
            list.add(url);
        }

        return list;
    }

    private static void findXY(Double latitude, Double longitude) {
//        System.out.println("latitude = " + latitude);
//        System.out.println("longitude = " + longitude);

        int mapHeight = 370;
        int mapWidth = 380;
        int maxLat = 85;
        int maxLong = 180;


//        System.out.println("x = "+ (latitude - maxLat));
//        System.out.println("y = " + (longitude - maxLong));

//
//        top left =
//        Latitude: 84.73838712095339
//        Longitude: -178.59375
//
//        top right =
//        Latitude: 84.73838712095339
//        Longitude: 175.078125
//
//        bottom left =
//        Latitude: -84.92832092949963
//        Longitude: -176.484375
//
//        bottom right=
//        Latitude: -84.60784045604663
//        Longitude: 172.96875


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
