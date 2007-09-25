package com.jamescookie.facebook.api;

import com.facebook.api.FacebookException;
import com.facebook.api.FacebookMethod;
import com.facebook.api.FacebookRestClient;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.input.DOMBuilder;

import java.io.IOException;

public class MyFacebookRestClient extends FacebookRestClient {
    protected Logger log = Logger.getLogger(this.getClass());

    public MyFacebookRestClient(String apiKey, String secret) {
        super(apiKey, secret);
    }

    public void setUserPreference(int key, String value) throws FacebookException, IOException {
        assert (value != null);
        assert (key >= 0 && key <= 200);
        callMethod(FacebookMethod.SET_USER_PREF,
                new Pair<String, CharSequence>("pref_id", String.valueOf(key)),
                new Pair<String, CharSequence>("value", value));
    }

    public String getUserPreference(int key) throws FacebookException, IOException {
        assert (key >= 0 && key <= 200);
        Document document = new DOMBuilder().build(
                callMethod(FacebookMethod.GET_USER_PREF,
                        new Pair<String, CharSequence>("pref_id", String.valueOf(key))));
        log.debug("Getting user preference: " + document);
        return document.getRootElement().getTextTrim();
    }

}
