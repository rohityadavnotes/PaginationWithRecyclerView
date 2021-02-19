package com.pagination.with.recyclerview.remote;

import android.content.Context;

public class ServiceGenerator {

    /**
     * Retrofit Service Generator class which initializes the calling RetrofitService
     *
     * @param context -  getApplicationContext.
     * @param serviceClass -  The Retrofit Service Interface class.
     */
    public static <S> S createService(Context context, Class<S> serviceClass) {
        return RetrofitInstance.getInstance(context, HttpConstants.BASE_URL).create(serviceClass);
    }
}
