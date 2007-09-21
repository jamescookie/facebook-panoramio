package com.jamescookie.facebook;

import com.jamescookie.facebook.api.MyFacebookRestClient;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class StartAction extends ActionSupport implements SessionAware {
    private static final String API_KEY = "40301fa6f17493c04408ec752c98ac1a";
    private static final String SECRET_KEY = "276e644d20c1c86e8be0d4d90514ca03";
    private Logger log = Logger.getLogger(this.getClass());
    private String auth_token;
    private Map session;
    private static final String FACEBOOK_CLIENT = "FACEBOOK_CLIENT";
    private MyFacebookRestClient client;

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public MyFacebookRestClient getClient() {
        return client;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String execute() throws Exception {
        MyFacebookRestClient client;

        if (session.containsKey(FACEBOOK_CLIENT)) {
            client = (MyFacebookRestClient) session.get(FACEBOOK_CLIENT);
            return forwardToNextAction(client);
        }

        client = new MyFacebookRestClient(API_KEY, SECRET_KEY);
        client.setIsDesktop(false);

        log.debug("Authenticating Facebook client using authToken from user...");
        String authToken = auth_token;
        log.debug("authToken = " + authToken);
        if (authToken == null || authToken.length() == 0) {
            return LOGIN;
        }
        try {
            client.auth_getSession(authToken);
            //noinspection unchecked
            session.put(FACEBOOK_CLIENT, client);
            return forwardToNextAction(client);
        } catch (Exception e) {
            log.error("unable to get a session with the key: " + authToken, e);
            return ERROR;
        }
    }

    private String forwardToNextAction(MyFacebookRestClient client) {
        this.client = client;
        return SUCCESS;
    }

}
