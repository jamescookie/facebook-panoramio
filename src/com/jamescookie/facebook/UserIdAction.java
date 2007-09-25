package com.jamescookie.facebook;

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

    public String execute() throws Exception {
        userId = getClient().getUserPreference(USER_ID);
        if (userId == null || userId.length() == USER_ID) {
            return INPUT;
        }

        return SUCCESS;
    }

    public String doChange() {
        return INPUT;
    }

    public String doSave() throws Exception {
        log.debug("Saving userid = "+userId);
        if (userId == null || userId.length() == USER_ID) {
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
            } catch (Exception e) {
                log.error("error saving userId: "+userId, e);
                errorMessage = "There was a problem saving your User Id. Please try again.";
                return INPUT;
            }
        }
        return SUCCESS;
    }

}
