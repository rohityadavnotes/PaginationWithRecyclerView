package com.pagination.with.recyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pagination.with.recyclerview.model.User;
import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = PaginationAdapter.class.getSimpleName();

    private List<User> list;
    private Context context;

    private static final int VIEW_TYPE_ITEM     = 0;
    private static final int VIEW_TYPE_LOADING  = 1;

    private boolean isLoaderVisible = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback paginationAdapterCallback;
    private String errorMessage = null;

    public PaginationAdapter(Context context) {
        this.context                    = context;
        this.paginationAdapterCallback  = (PaginationAdapterCallback) context;
        list                            = new ArrayList<>();
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case VIEW_TYPE_ITEM:
                viewHolder = getViewHolder(viewGroup, layoutInflater);
                break;
            case VIEW_TYPE_LOADING:
                View loadingView    = layoutInflater.inflate(R.layout.item_loading_with_retry, viewGroup, false);
                viewHolder          = new LoadingViewHolder(loadingView);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, LayoutInflater layoutInflater) {
        RecyclerView.ViewHolder viewHolder;
        View itemView   = layoutInflater.inflate(R.layout.item_row, viewGroup, false);
        viewHolder      = new ItemViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_ITEM:
                User result = list.get(position);
                ((ItemViewHolder) viewHolder).bindData(result);
                break;
            case VIEW_TYPE_LOADING:
                ((LoadingViewHolder) viewHolder).bindData();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size() - 1 && isLoaderVisible) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    /* Helpers */
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoaderVisible = true;
        add(new User());
    }

    public void removeLoadingFooter() {
        isLoaderVisible = false;

        int position = list.size() - 1;
        User result = getItem(position);

        if (result != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(User item) {
        list.add(item);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<User> itemList) {
        for (User item : itemList) {
            add(item);
        }
    }

    public User getItem(int position) {
        return list.get(position);
    }

    public void remove(User item) {
        int position = list.indexOf(item);
        if (position > -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoaderVisible = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMessage
     */
    public void showRetry(boolean show, @Nullable String errorMessage) {
        retryPageLoad = show;
        notifyItemChanged(list.size() - 1);

        if (errorMessage != null) this.errorMessage = errorMessage;
    }

    /* View Holders */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView userImageView;
        private ProgressBar progressBar;
        private TextView userNameTextView;
        private TextView phoneNumberTextView;

        private ItemViewHolder(View view) {
            super(view);
            userImageView       = view.findViewById(R.id.userImageView);
            progressBar         = view.findViewById(R.id.progressBar);
            userNameTextView    = view.findViewById(R.id.userNameTextView);
            phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
        }

        private void bindData(User item) {
            String categoryImageUrl = "https://backend24.000webhostapp.com/Json/profile.jpg";
            Glide.with(context)
                    .load(categoryImageUrl)
                    .error(R.drawable.placeholder)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(userImageView);

            userNameTextView.setText(item.getFirstName() + " " + item.getLastName());
            phoneNumberTextView.setText(item.getPhoneNumber());
        }
    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar progressBar;
        private ImageButton retryImageButton;
        private TextView errorMessageTextView;
        private LinearLayout errorLinearLayout;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar             = itemView.findViewById(R.id.progressBar);
            retryImageButton        = itemView.findViewById(R.id.retryImageButton);
            errorMessageTextView    = itemView.findViewById(R.id.errorMessageTextView);
            errorLinearLayout       = itemView.findViewById(R.id.errorLinearLayout);

            retryImageButton.setOnClickListener(this);
            errorLinearLayout.setOnClickListener(this);
        }

        private void bindData() {
            progressBar.setIndeterminate(true);
            if (retryPageLoad) {
                errorLinearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                errorMessageTextView.setText(errorMessage != null ? errorMessage : "An unknown error has occurred. Please try again later.");
            } else {
                errorLinearLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.retryImageButton:
                case R.id.errorLinearLayout:
                    showRetry(false, null);
                    paginationAdapterCallback.retryPageLoad();
                    break;
            }
        }
    }
}
