package com.pagination.with.recyclerview.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    /* The initial index of the page that'll start from */
    public static final int PAGE_START = 1;

    /**
     * Set scrolling threshold
     * Meaning : Scroll position where to start loading more items
     *
     * here (for now i'm assuming after 1 item loaded then scroll listener call)
     */
    private static final int VISIBLE_THRESHOLD = 1;

    @NonNull
    private LinearLayoutManager linearLayoutManager;

    /**
     * Supporting only LinearLayoutManager for now.
     */
    public PaginationScrollListener(@NonNull LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount            = linearLayoutManager.getChildCount();
        int totalItemCount              = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition    = linearLayoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage())
        {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= VISIBLE_THRESHOLD)
            {
                loadMoreItems();
            }
        }
    }

    /*@Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount            = linearLayoutManager.getChildCount();
        int totalItemCount              = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition    = linearLayoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage())
        {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE)
            {
                loadMoreItems();
            }
        }
    }*/

    protected abstract void loadMoreItems();
    public abstract boolean isLastPage();
    public abstract boolean isLoading();
}