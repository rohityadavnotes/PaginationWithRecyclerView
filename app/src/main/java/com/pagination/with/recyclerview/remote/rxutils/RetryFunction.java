package com.pagination.with.recyclerview.remote.rxutils;

import android.util.Log;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class RetryFunction implements Function<Observable<Throwable>, ObservableSource<?>> {

    public static final String TAG = RetryFunction.class.getSimpleName();

    private int retryDelaySeconds; /* retry delay time */
    private int retryCount; /* record the current number of retries */
    private int maxRetries; /* maximum number of retries */

    public RetryFunction(int retryDelaySeconds, int maxRetries) {
        this.retryDelaySeconds = retryDelaySeconds;
        this.maxRetries = maxRetries;
    }

    @Override
    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {

                        /*
                         * If the cause of the failure is UnknownHostException (DNS resolution failure,
                         * currently no network), it is not necessary to retry the request directly to
                         * end error correction
                         */
                        if (throwable instanceof UnknownHostException) {
                            return Observable.error(throwable);
                        }

                        /* do not exceed the maximum number of retries, then the retried */
                        if (++retryCount <= maxRetries) {
                            /* When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed). */
                            Log.d(TAG, "Observable get error, it will try after " + retryDelaySeconds + " second, retry count " + retryCount);
                            return Observable.timer(retryDelaySeconds, TimeUnit.SECONDS);
                        }

                        /* Max retries hit. Just pass the error along. */
                        return Observable.error(throwable);
                    }
                });
    }
}
