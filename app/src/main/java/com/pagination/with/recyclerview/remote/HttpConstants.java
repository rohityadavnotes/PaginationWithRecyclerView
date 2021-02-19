package com.pagination.with.recyclerview.remote;

import com.pagination.with.recyclerview.BuildConfig;

/**
 * Define constants here
 * e.g.
 * public static final String EXTRA_PARAM_NAME = "extra_param_name"
 */
public class HttpConstants {

    public static final String CONTENT_TYPE             = "Content-Type";
    public static final String ACCEPT                   = "Accept";
    public static final String USER_AGENT               = "User-Agent";
    public static final String AUTHORIZATION            = "Authorization";
    public static final String HEADER_CACHE_CONTROL     = "Cache-Control";
    public static final String HEADER_PRAGMA            = "Pragma";

    /*
     * 5 Second
     * 7 Days ago
     */
    public static final int MAX_AGE                     = 5;
    public static final int MAX_STALE                   = 7;

    /*
     * 60 Second
     * 7 Days ago
     */
    public static final int MAX_AGE_STRING              = 60;
    public static final int MAX_STALE_STRING            = 60 * 60 * 24 * 7;

    public static final String MAX_AGE_CACHE_STRING     = "public, max-age="+MAX_AGE_STRING;
    public static final String MAX_STALE_CACHE_STRING   = "public, only-if-cached, max-stale="+MAX_STALE_STRING;


    public static final String OK_HTTP_CACHE_DIR           = "okhttp_cache";
    /*
     * 10 MB
     */
    public static final long OK_HTTP_CACHE_SIZE            = 10 * 1024 * 1024;

    /*
     * Note :  We can set timeouts settings on the underlying HTTP client.
     * If we don’t specify a client, Retrofit will create one with default connect and read timeouts.
     * By default, Retrofit uses the following timeouts :
     *                                                      Connection timeout: 10 seconds
     *                                                      Read timeout: 10 seconds
     *                                                      Write timeout: 10 seconds
     */
    public static final int DEFAULT_HTTP_CONNECT_TIMEOUT             = 1;
    public static final int DEFAULT_HTTP_READ_TIMEOUT                = 30;
    public static final int DEFAULT_HTTP_WRITE_TIMEOUT               = 15;

    /*
     * Network Base Config
     */
    public static final String HOST_URL                 = BuildConfig.HOST_URL;
    /*
     * Note : If you use Retrofit 2 then add / at END. if use Retrofit 1 so remove it
     */
    public static final String BASE_URL                 = "https://backend24.000webhostapp.com/RestApi/";
    public static final String API_ACCESS_KEY           = "key";
    public static final String AUTHENTICATION_TOKEN     = "";

    /*
     * End points
     * Note : If you use Retrofit 1 then add / at START.
     */
    public static final String GET_USERS                = "middleware/GetUsers.php";
}
