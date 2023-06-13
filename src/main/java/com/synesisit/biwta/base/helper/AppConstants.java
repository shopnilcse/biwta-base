package com.synesisit.biwta.base.helper;

public class AppConstants {

    //public static final String MIDDLEWARE_BASE_URL = "http://103.92.84.106:91/";
    public static final String MIDDLEWARE_BASE_URL = "http://172.22.11.36:8300/";

    public static final String GET_DU_STUDENT = MIDDLEWARE_BASE_URL + "getdustudent?registrationNo=";

    public static final String GET_NU_STUDENT = MIDDLEWARE_BASE_URL + "getnustudent?registrationno=";

    public static final String GET_IAU_STUDENT = MIDDLEWARE_BASE_URL + "getiaustudent?registrationNo=";

    //public static final String DSHE_BASE_URL= "http://dshe.online:7080/";

    public static final String DSHE_BASE_URL= "http://118.67.223.30:7080/";

    public static final String GET_DHSE_API_ACCESS_TOKEN_URL = DSHE_BASE_URL + "sso/Services/Security/PublicUser/MerchantSignIn";

    public static final String VERIFY_DHSE_API_ACCESS_TOKEN_URL = DSHE_BASE_URL + "sso/Services/Security/PublicUser/IsTokenValid";

    public static final String GET_DHSE_API_ACCESS_TOKEN_USERNAME = "rubel";

    public static final String GET_DHSE_API_ACCESS_TOKEN_PASSWORD = "123456";

    public static final String GET_DHSE_ACCOUNT_LOGIN_URL = DSHE_BASE_URL + "sso/Account/Login?MerchantId="+ AppConstants.GET_DHSE_ACCOUNT_LOGIN_MERCHANTID + "&token=";

    public static final String GET_DHSE_ACCOUNT_LOGIN_MERCHANTID = "1234567";

    //public static final String GET_PMEAT_WEB_LOGIN_URL = "http://103.92.84.244:8099/pmeat/#/auth/login/dshe/redirect";
    public static final String GET_PMEAT_WEB_LOGIN_URL = "http://estipend.pmeat.gov.bd/#/auth/login/dshe/redirect";

    public static final String AUTH_MODULE_URL = "http://localhost:7200/";
}
