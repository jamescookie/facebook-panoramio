package com.jamescookie.facebook;

import com.jamescookie.facebook.api.MyFacebookRestClient;

public class UserIdAction extends CommonAction {
    private static final int USER_ID = 0;
    private String userId;
    private String errorMessage;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getAnyError() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String execute() {
        try {
            userId = getUserIdFromFaceBook(getClient());
        } catch (NoClientException e) {
            return LOGIN;
        } catch (Exception e) {
            log.error("error getting userId", e);
            return ERROR;
        }
        if (userId == null || userId.length() == 0) {
            return INPUT;
        }

        return SUCCESS;
    }

    public static String getUserIdFromFaceBook(MyFacebookRestClient client) throws Exception {
        return client.getUserPreference(USER_ID);
    }

    public String doChange() {
        return INPUT;
    }

    public String doSave() {
        log.debug("Saving userid = "+userId);
        if (userId == null || userId.length() == 0) {
            errorMessage = "User Id must be entered";
            return INPUT;
        } else {
            try {
                Integer.parseInt(userId);
            } catch (NumberFormatException e) {
                errorMessage = "User Id must be a number";
                return INPUT;
            }
            try {
                getClient().setUserPreference(USER_ID, userId);
            } catch (NoClientException e) {
                return LOGIN;
            } catch (Exception e) {
                log.error("error saving userId: "+userId, e);
                errorMessage = "There was a problem saving your User Id. Please try again.";
                return INPUT;
            }
        }
        return SUCCESS;
    }

    public MyFacebookRestClient getClient() throws NoClientException {
        MyFacebookRestClient client = super.getClient();
        if (client == null) {
            throw new NoClientException();
        }
        return client;
    }

}
