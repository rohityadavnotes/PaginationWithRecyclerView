package com.pagination.with.recyclerview.remote.rxutils;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import com.pagination.with.recyclerview.utilities.LogcatUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * DisposableManager, prevent memory leaks
 */
public class DisposablePool {

    public static final String TAG = DisposablePool.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static DisposablePool instance;
    private ConcurrentHashMap<Object, CompositeDisposable> concurrentHashMap = new ConcurrentHashMap<>();

    public static DisposablePool getInstance() {
        if (instance == null) {
            synchronized (DisposablePool.class) {
                if (instance == null) {
                    instance = new DisposablePool();
                }
            }
        }
        return instance;
    }

    /**
     * Manage subscription according to tagName [Registration Subscription Information]
     *
     * @param tagName logo
     * @param disposable subscription information
     */
    public Disposable add(@NonNull Object tagName, Disposable disposable) {
        CompositeDisposable compositeDisposable = concurrentHashMap.get(tagName);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            concurrentHashMap.put(tagName, compositeDisposable);
            LogcatUtils.errorMessage(TAG,"Call add(@NonNull Object tagName, Disposable disposable)");
        }
        compositeDisposable.add(disposable);
        return disposable;
    }

    /**
     * Manage subscription according to tagName [Registration Subscription Information]
     *
     * @param disposable subscription information
     * @param tagName logo
     */
    public Disposable add(Disposable disposable, @NonNull Object tagName) {
        CompositeDisposable compositeDisposable = concurrentHashMap.get(tagName);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            concurrentHashMap.put(tagName, compositeDisposable);
        }
        compositeDisposable.add(disposable);
        return disposable;
    }

    /**
     * Cancel subscription [Cancel all subscription information in the logo]
     *
     * @param tagName logo
     */
    public void remove(@NonNull Object tagName) {
        CompositeDisposable compositeDisposable = concurrentHashMap.get(tagName);
        if (compositeDisposable != null) {
            compositeDisposable.dispose(); /* unsubscribe */
            concurrentHashMap.remove(tagName);
            LogcatUtils.errorMessage(TAG,"Call remove(@NonNull Object tagName)");
        }
    }

    /**
     * Cancel subscription [Single subscription cancellation]
     *
     * @param tagName logo
     * @param disposable subscription information
     */
    public void remove(@NonNull Object tagName, Disposable disposable) {
        CompositeDisposable compositeDisposable = concurrentHashMap.get(tagName);
        if (compositeDisposable != null) {
            compositeDisposable.remove(disposable);
            if (compositeDisposable.size() == 0) {
                concurrentHashMap.remove(tagName);
            }
        }
    }

    /**
     * Cancel all subscriptions
     */
    public void removeAll() {
        Iterator<Map.Entry<Object, CompositeDisposable>> iterator = concurrentHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, CompositeDisposable> entry = iterator.next();
            CompositeDisposable compositeDisposable = entry.getValue();
            if (compositeDisposable != null) {
                compositeDisposable.dispose(); /* unsubscribe */
                iterator.remove();
            }
        }
        concurrentHashMap.clear();
    }

    /**
     * unsubscribe
     *
     * @param disposable subscription information
     */
    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            LogcatUtils.errorMessage(TAG,"Call dispose(Disposable disposable)");
        }
    }
}
