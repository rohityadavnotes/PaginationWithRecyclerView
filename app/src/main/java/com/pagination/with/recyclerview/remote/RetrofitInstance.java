package com.pagination.with.recyclerview.remote;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.pagination.with.recyclerview.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import static android.util.Log.VERBOSE;

public class RetrofitInstance {

    private static Retrofit retrofitInstance = null;

    /*
     * This is a Private Constructor
     * So that nobody can create an object with this constructor, from outside of this class.
     * We will achieve Singleton
     */
    private RetrofitInstance() {
    }

    public static Retrofit getInstance(Context context, String baseUrl) {
        if ( retrofitInstance == null)
        {
            /* thread safe Singleton implementation */
            synchronized (RetrofitInstance.class)
            {
                if (retrofitInstance == null)
                {
                    retrofitInstance = getRetrofitInstance(context, baseUrl);
                }
            }
        }
        return  retrofitInstance;
    }


    public static Retrofit getRetrofitInstance(Context context, String baseUrl) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        OkHttpClient.Builder okHttpClientBuilder = getOkHttpClientBuilderInstance(context);
        OkHttpClient okHttpClient = okHttpClientBuilder.build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.client(okHttpClient);
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        Retrofit retrofitInstance = builder.build();
        return retrofitInstance;
    }

    private static OkHttpClient.Builder getOkHttpClientBuilderInstance(Context context) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        okHttpClientBuilder.connectTimeout(HttpConstants.DEFAULT_HTTP_CONNECT_TIMEOUT, TimeUnit.MINUTES);
        okHttpClientBuilder.writeTimeout(HttpConstants.DEFAULT_HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(HttpConstants.DEFAULT_HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG)
        {
            okHttpClientBuilder.addInterceptor(provideLoggingInterceptor(context));
        }

        return okHttpClientBuilder;
    }

    private static LoggingInterceptor provideLoggingInterceptor(Context context) {
        return new LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .log(VERBOSE)
                .build();
    }
}
