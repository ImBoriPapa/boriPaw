package com.boriworld.boriPaw.common.constant;


public class ApiEndpoints {

    private static final String VERSION = "/v1";
    private static final String API_PATH = "/api" + VERSION;

    //User Accounts
    public static final String ACCOUNTS_ROOT_PATH = API_PATH + "/user-accounts";
    public static final String GET_PROFILE = ACCOUNTS_ROOT_PATH + "/{user-accountsId}/profile";

    //User Profile
    public static final String CHANGE_PROFILE_NICKNAME = GET_PROFILE + "/nickname";
    public static final String CHANGE_PROFILE_INTRODUCE = GET_PROFILE + "/introduce";

    //Authentication
    public static final String ME = ACCOUNTS_ROOT_PATH + "/me";
    public static final String LOGIN_PATH = API_PATH + "/login";
    public static final String RE_ISSUE_PATH = API_PATH + "/reissue";
    public static final String LOGOUT_PATH = API_PATH + "/logout/{userAccountId}";

    //Follow
    public static final String FOLLOW_ROOT_PATH = API_PATH + "/follow";
    public static final String UNFOLLOW = FOLLOW_ROOT_PATH + "/{followId}";
    public static final String GET_FOLLOWERS = FOLLOW_ROOT_PATH + "/followers";
    public static final String GET_FOLLOWINGS = FOLLOW_ROOT_PATH + "/followings";
}
