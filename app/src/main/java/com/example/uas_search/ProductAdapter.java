package com.example.uas_search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductItem> searchResponse;

    public ProductAdapter(List<ProductItem> searchResponse) {
        this.searchResponse = searchResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem resultItem = searchResponse.get(position);
        holder.tvHeadlines.setText(resultItem.getTitle());
        holder.tvDescription.setText(resultItem.getDescription());
        Glide.with(holder.itemView)
                .load(resultItem.getThumbnail())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image while loading
                        .error(R.drawable.ic_launcher_background) // Error image if loading fails
                        .diskCacheStrategy(DiskCacheStrategy.ALL)) // Caching options
                .into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return searchResponse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHeadlines;
        public TextView tvDescription;
        public ImageView  imgProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeadlines = itemView.findViewById(R.id.tvHeadlines);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            imgProduct = itemView.findViewById(R.id.Thumbnail);
        }
    }
}
