package com.jamescookie.facebook.api;

import org.w3c.dom.Document;

import java.io.IOException;

import com.facebook.api.FacebookRestClient;
import com.facebook.api.FacebookException;
import com.facebook.api.FacebookMethod;

public class MyFacebookRestClient extends FacebookRestClient {

    public MyFacebookRestClient(String apiKey, String secret) {
        super(apiKey, secret);
    }

    public Document setUserPreference(int key, String value) throws FacebookException, IOException {
         assert (value != null);
         assert (key >= 0 && key <= 200);
         return callMethod(FacebookMethod.SET_USER_PREF,
               new Pair<String, CharSequence>("pref_id", String.valueOf(key)),
               new Pair<String, CharSequence>("value", value));
     }

     public Document getUserPreference(int key) throws FacebookException, IOException {
         assert (key >= 0 && key <= 200);
         return callMethod(FacebookMethod.GET_USER_PREF,
               new Pair<String, CharSequence>("pref_id", String.valueOf(key)));
     }

}
