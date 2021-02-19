package com.pagination.with.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.pagination.with.recyclerview.decoration.ItemDividerDecoration;
import com.pagination.with.recyclerview.listener.PaginationScrollListener;
import com.pagination.with.recyclerview.model.Page;
import com.pagination.with.recyclerview.model.User;
import com.pagination.with.recyclerview.remote.HttpConstants;
import com.pagination.with.recyclerview.remote.HttpHelper;
import com.pagination.with.recyclerview.remote.RetrofitInstance;
import com.pagination.with.recyclerview.remote.RetrofitService;
import com.pagination.with.recyclerview.remote.observer.BaseResponse;
import com.pagination.with.recyclerview.remote.observer.BaseResponseObserver;
import com.pagination.with.recyclerview.remote.rxutils.DisposablePool;
import com.pagination.with.recyclerview.utilities.LogcatUtils;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import static com.pagination.with.recyclerview.listener.PaginationScrollListener.PAGE_START;

public class PaginationActivity extends AppCompatActivity implements PaginationAdapterCallback {

    private static final String TAG = PaginationActivity.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout errorLinearLayout;
    private Button retryButton;
    private TextView errorTextView;

    private LinearLayoutManager linearLayoutManager;
    private PaginationAdapter paginationAdapter;

    private int currentPage     = PAGE_START;
    private boolean isLoading   = false;
    private boolean isLastPage  = false;
    private int TOTAL_PAGES     = 1; /* default 1, dynamically add using api*/

    private RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination);
        initializeView();
        initializeObject();
        initializeToolBar();
        onTextChangedListener();
        initializeEvent();
    }

    protected void initializeView() {
        swipeRefreshLayout  = findViewById(R.id.swipeRefreshLayout);
        recyclerView        = findViewById(R.id.recyclerView);
        progressBar         = findViewById(R.id.progressBar);
        errorLinearLayout   = findViewById(R.id.errorLinearLayout);
        retryButton         = findViewById(R.id.retryButton);
        errorTextView       = findViewById(R.id.errorTextView);
    }

    protected void initializeObject() {
        retrofitService = HttpHelper.getInstance().getService(RetrofitService.class, RetrofitInstance.getInstance(getApplicationContext(), HttpConstants.BASE_URL));

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemDividerDecoration itemDecoration = new ItemDividerDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        paginationAdapter = new PaginationAdapter(this);
        recyclerView.setAdapter(paginationAdapter);
    }

    protected void initializeToolBar() {

    }

    protected void onTextChangedListener() {

    }

    protected void initializeEvent() {
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;

                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        loadFirstPage();

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFirstPage();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(getClass().getSimpleName(), "refresh");
                doRefresh();
            }
        });
    }

    private void showErrorView(String errorMessage) {
        if (errorLinearLayout.getVisibility() == View.GONE) {
            errorLinearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            errorTextView.setText(errorMessage);
        }
    }

    private void hideErrorView() {
        if (errorLinearLayout.getVisibility() == View.VISIBLE) {
            errorLinearLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private Observable<Response<BaseResponse<Page>>> callApi() {
        Observable<Response<BaseResponse<Page>>> pageObservable = retrofitService.getPage(HttpConstants.API_ACCESS_KEY, "" + currentPage);
        return pageObservable;
    }

    private List<User> fetchResults(BaseResponse<Page> baseResponse) {
        return baseResponse.getData().getUsers();
    }

    private void loadFirstPage() {
        /* To ensure list is visible when retry button in error view is clicked */
        hideErrorView();
        isLoading   = false;
        isLastPage  = false;
        currentPage = PAGE_START;

        BaseResponseObserver<Page> baseObserver = new BaseResponseObserver<Page>() {
            @Override
            public String setTag() {
                return "baseObserver";
            }

            @Override
            public void onBegin(Disposable disposable) {
                DisposablePool.getInstance().add(setTag(), disposable);
            }

            @Override
            public void onSuccess(BaseResponse<Page> baseResponse) {
                int code = baseResponse.getCode();
                if (code == 200)
                {
                    List<User> results = fetchResults(baseResponse);
                    TOTAL_PAGES = baseResponse.getData().getTotalNumberOfPages();

                    paginationAdapter.addAll(results);
                    progressBar.setVisibility(View.GONE);

                    if (currentPage <= TOTAL_PAGES)
                    {
                        paginationAdapter.addLoadingFooter();
                    }
                    else
                    {
                        isLastPage = true;
                    }
                }
                else
                {
                    LogcatUtils.informationMessage(TAG, "Error 2 : " + baseResponse.getMessage());
                    showErrorView(baseResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                LogcatUtils.informationMessage(TAG, "Error 1 : " + errorMessage);
                DisposablePool.getInstance().remove(setTag());
                showErrorView(errorMessage);
            }

            @Override
            public void onFinish() {
                DisposablePool.getInstance().remove(setTag());
            }
        };

        callApi()
                .compose(HttpHelper.<Response<BaseResponse<Page>>>setThread())
                /* Subscribe */
                .subscribe(baseObserver);
    }

    private void loadNextPage() {
        BaseResponseObserver<Page> baseObserver = new BaseResponseObserver<Page>() {
            @Override
            public String setTag() {
                return "baseObserver";
            }

            @Override
            public void onBegin(Disposable disposable) {
                DisposablePool.getInstance().add(setTag(), disposable);
            }

            @Override
            public void onSuccess(BaseResponse<Page> baseResponse) {
                int code = baseResponse.getCode();
                if (code == 200)
                {
                    paginationAdapter.removeLoadingFooter();
                    isLoading = false;

                    List<User> results = fetchResults(baseResponse);
                    paginationAdapter.addAll(results);

                    if (currentPage != TOTAL_PAGES)
                    {
                        paginationAdapter.addLoadingFooter();
                    }
                    else
                    {
                        isLastPage = true;
                    }
                }
                else
                {
                    LogcatUtils.informationMessage(TAG, "Error 2 : " + baseResponse.getMessage());
                    paginationAdapter.showRetry(true, baseResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                LogcatUtils.informationMessage(TAG, "Error 1 : " + errorMessage);
                DisposablePool.getInstance().remove(setTag());
                paginationAdapter.showRetry(true, errorMessage);
            }

            @Override
            public void onFinish() {
                DisposablePool.getInstance().remove(setTag());
            }
        };

        callApi()
                .compose(HttpHelper.<Response<BaseResponse<Page>>>setThread())
                /* Subscribe */
                .subscribe(baseObserver);
    }

    /**
     * Triggers the actual background refresh via the {@link SwipeRefreshLayout}
     */
    private void doRefresh() {
        progressBar.setVisibility(View.VISIBLE);
        DisposablePool.getInstance().remove("baseObserver");
        paginationAdapter.getList().clear();
        paginationAdapter.notifyDataSetChanged();
        loadFirstPage();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void retryPageLoad() {
        loadNextPage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                swipeRefreshLayout.setRefreshing(true);
                doRefresh();
        }
        return super.onOptionsItemSelected(item);
    }
}