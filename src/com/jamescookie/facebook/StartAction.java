package com.jamescookie.facebook;

import com.jamescookie.facebook.api.MyFacebookRestClient;

public class StartAction extends CommonAction {
    private static final String API_KEY = "40301fa6f17493c04408ec752c98ac1a";
    private static final String SECRET_KEY = "276e644d20c1c86e8be0d4d90514ca03";
    private String auth_token;

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public String execute() {
        MyFacebookRestClient client = getClient();

        if (client != null) {
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
            return forwardToNextAction(client);
        } catch (Exception e) {
            log.error("unable to get a session with the key: " + authToken, e);
            return ERROR;
        }
    }

    private String forwardToNextAction(MyFacebookRestClient client) {
        setClient(client);
        try {
            if (!client.users_isAppAdded()) {
                return INPUT;
            }
        } catch (Exception e) {
            log.error("unable to tell if app is added", e);
            return ERROR;
        }
        return SUCCESS;
    }

}
