package com.pagination.with.recyclerview.remote;

import com.pagination.with.recyclerview.model.Page;
import com.pagination.with.recyclerview.remote.observer.BaseResponse;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {

    @FormUrlEncoded
    @POST(HttpConstants.GET_USERS)
    Observable<Response<BaseResponse<Page>>> getPage(
            @Field("apiKey") String key,
            @Field("pageNumber") String pageNumber);
}
