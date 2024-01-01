package com.boriworld.boriPaw.common.constant;


public class ApiEndpoints {
    private static final String API_PATH = "/api";

    //UserAccounts
    public static final String ACCOUNTS_ROOT_PATH = API_PATH + "/userAccounts";
    public static final String GET_PROFILE = ACCOUNTS_ROOT_PATH + "/{userAccountId}/profile";

    //Authentication
    public static final String ME = API_PATH + "/me";
    public static final String LOGIN_PATH = API_PATH + "/login";
    public static final String RE_ISSUE_PATH = API_PATH + "/reissue";
    public static final String LOGOUT_PATH = API_PATH + "/logout/{userAccountId}";

    //Follow
    public static final String FOLLOW_ROOT_PATH = API_PATH + "/follow";
    public static final String UNFOLLOW = FOLLOW_ROOT_PATH + "/{followId}";
    public static final String GET_FOLLOWER = API_PATH + "/follower";
    public static final String GET_FOLLOWING = API_PATH + "/following";
}
