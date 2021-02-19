package com.pagination.with.recyclerview.remote;

import com.pagination.with.recyclerview.remote.rxutils.RetryFunction;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HttpHelper {

    public static final String TAG = HttpHelper.class.getSimpleName();

    private static HttpHelper instance;

    public static HttpHelper getInstance() {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new HttpHelper();
                }
            }
        }
        return instance;
    }

    public <S> S getService(Class<S> serviceClass, Retrofit retrofit) {
        return retrofit.create(serviceClass);
    }

    /*
     * Call like,
     *
     * Example : singleEmployeeDetailsObservable.compose(HttpHelper.<Response<BaseResponse<SingleEmployeeDetails>>>setThreadWithRetry()).subscribe(singleEmployeeDetailsObserver);
     */
    public static <T> ObservableTransformer<T, T> setThreadWithRetry(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new RetryFunction(3, 3));
            }
        };
    }

    /*
     * Call like,
     *
     * Example : singleEmployeeDetailsObservable.compose(HttpHelper.<Response<BaseResponse<SingleEmployeeDetails>>>setThread()).subscribe(singleEmployeeDetailsObserver);
     */
    public static <T> ObservableTransformer<T, T> setThread(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        /* Update the UI on the main thread after the request is completed */
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
