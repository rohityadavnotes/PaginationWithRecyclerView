package com.pagination.with.recyclerview.remote.observer;

import android.app.ProgressDialog;
import android.content.Context;
import com.pagination.with.recyclerview.remote.exception.ExceptionDetails;
import com.pagination.with.recyclerview.remote.exception.ExceptionHandler;
import com.pagination.with.recyclerview.remote.exception.HTTPResponseStatusCodes;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/*
 * General Observer set for specific format
 * Users can customize their own class according to their own needs to inherit BaseDataObserver<T>
 *
 * Apply to
 *          {
 *              "status": true,
 *              "code": 200,
 *              "message": "Get page successfully",
 *              "data": {
 *                          "current_page_number": 1,
 *                          "total_number_of_items": 2,
 *                          "item_in_one_page": 5,
 *                          "total_number_of_pages": 1,
 *                          "users": [{
 *                                      "id": "14",
 *                                      "first_name": "Rohit",
 *                                      "last_name": "Yadav",
 *                                      "gender": "MALE",
 *                                      "phone_number": "7898680304",
 *                                      "phone_number_verified": "1",
 *                                      "email": "rohitnotes24@gmail.com",
 *                                      "email_verified": "1",
 *                                      "password": "12345",
 *                                      "fcm_token": "sdfa",
 *                                      "last_login": "2021-02-11 07:48:24",
 *                                      "created_at": "2021-02-11 07:43:40",
 *                                      "updated_at": "2021-02-11 07:56:07",
 *                                      "expired_at": "2021-02-11 07:43:40",
 *                                      "account_verified_by_admin": "1"
 *                                    }, {
 *                                      "id": "15",
 *                                      "first_name": "Lucky",
 *                                      "last_name": "Yadav",
 *                                      "gender": "MALE",
 *                                      "phone_number": "7898680304",
 *                                      "phone_number_verified": "1",
 *                                      "email": "iamrohityadav24@gmail.com",
 *                                      "email_verified": "1",
 *                                      "password": "password",
 *                                      "fcm_token": "adaerwerrewerfsd",
 *                                      "last_login": "2021-02-11 07:44:35",
 *                                      "created_at": "2021-02-11 07:44:35",
 *                                      "updated_at": "2021-02-11 07:44:35",
 *                                      "expired_at": "2021-02-11 07:44:35",
 *                                      "account_verified_by_admin": "1"
 *                                   }]
 *                       }
 *              }
 */
public abstract class BaseResponseObserver<T> implements io.reactivex.Observer<retrofit2.Response<BaseResponse<T>>> {

    private boolean showDialog;
    private ProgressDialog progressDialog;
    private Context context;

    public BaseResponseObserver(Context context, Boolean showDialog) {
        this.context    = context;
        this.showDialog = showDialog;
    }

    public BaseResponseObserver(Context context) {
        this(context,true);
    }

    public BaseResponseObserver() {
    }

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        onBegin(disposable);
        if (progressDialog == null && showDialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading");
            progressDialog.show();
        }
    }

    @Override
    public void onNext(@NonNull retrofit2.Response<BaseResponse<T>> response) {
        System.out.println("code : "+response.code());
        System.out.println("errorBody : "+response.errorBody());
        System.out.println("body : "+response.body());

        if (response.isSuccessful())
        {
            BaseResponse<T> baseResponse = response.body();
            onSuccess(baseResponse);
        }
        else
        {
            ExceptionDetails exceptionDetails = HTTPResponseStatusCodes.getCodeInfo(response.code());
            String errorMessage = exceptionDetails.getMessage();
            onFailure(errorMessage);
        }
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        hidDialog();
        String errorMessage = ExceptionHandler.getErrorMessage(throwable);
        onFailure(errorMessage);
    }

    @Override
    public void onComplete() {
        hidDialog();
        onFinish();
    }

    public void hidDialog() {
        if (progressDialog != null && showDialog == true)
            progressDialog.dismiss();
        progressDialog = null;
    }

    /**
     * Tag the network request
     * @return string
     */
    public abstract String setTag();

    /**
     * OnSubscribe callback
     *
     * @param disposable
     */
    public abstract void onBegin(Disposable disposable);

    /**
     * onNext callback
     *
     * @param baseResponse
     */
    public abstract void onSuccess(BaseResponse<T> baseResponse);

    /**
     * onError callback
     *
     * @param errorMessage
     */
    public abstract void onFailure(String errorMessage);

    /**
     * onCompleted callback
     */
    public abstract void onFinish();
}
