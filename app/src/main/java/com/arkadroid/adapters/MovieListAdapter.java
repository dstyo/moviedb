package com.arkadroid.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkadroid.R;
import com.arkadroid.dagger.modules.GlideApp;
import com.arkadroid.dagger.modules.GlideRequest;
import com.arkadroid.dagger.modules.GlideRequests;
import com.arkadroid.models.MovieModel;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ListPreloader.PreloadSizeProvider<MovieModel>,
        ListPreloader.PreloadModelProvider<MovieModel> {

    private final LayoutInflater inflater;
    private List<MovieModel> movies = new ArrayList<>();
    private Activity activity;

    private OnMovieSelectionListener movieSelectionListener;

    private GlideRequest<Drawable> requestBuilder;

    private int[] actualDimensions;

    public MovieListAdapter(Activity activity) {

        this.activity = activity;
        inflater = LayoutInflater.from(activity);

        GlideRequests requestManager = GlideApp.with(activity);
        requestBuilder = requestManager.asDrawable().fitCenter();

        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.row_movie, parent, false);

        if (actualDimensions == null) {
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (actualDimensions == null) {
                        actualDimensions = new int[]{view.getWidth(), view.getHeight()};
                    }
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final MovieViewHolder holder = (MovieViewHolder) viewHolder;
        final MovieModel model = movies.get(position);

        String name = model.getTitle();
        holder.tvName.setText(name);

        String thumbnail = model.getPosterPath();
        requestBuilder.clone()
                .load(thumbnail)
                .placeholder(R.drawable.ic_live_tv_black_24dp)
                .error(R.drawable.ic_live_tv_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivThumbnail);

        GlideApp.with(activity).load(model.getLargePosterPath()).centerInside().preload();

        String rate = String.valueOf(model.getVoteAverage());
        holder.tvRate.setText(rate);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieSelectionListener != null) {
                    movieSelectionListener.onMovieSelected(model, holder.ivThumbnail, position);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public OnMovieSelectionListener getMovieSelectionListener() {
        return movieSelectionListener;
    }

    public void setMovieSelectionListener(OnMovieSelectionListener movieSelectionListener) {
        this.movieSelectionListener = movieSelectionListener;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieModel> movies) {
        if (movies == null) {
            return;
        }
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void notifyMoviesListChanged() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public List<MovieModel> getPreloadItems(int position) {
        return Collections.singletonList(movies.get(position));
    }

    @Nullable
    @Override
    public RequestBuilder getPreloadRequestBuilder(MovieModel item) {

        return requestBuilder
                .clone()
                .load(item.getPosterPath());
    }

    @Nullable
    @Override
    public int[] getPreloadSize(MovieModel item, int adapterPosition, int perItemPosition) {
        return actualDimensions;
    }

    public interface OnMovieSelectionListener {

        void onMovieSelected(MovieModel model, View view, int position);
    }

    private static class MovieViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView tvName;
        public TextView tvRate;
        public ImageView ivThumbnail;

        public MovieViewHolder(View view) {
            super(view);
            this.view = view;
            this.ivThumbnail = view.findViewById(R.id.ivThumbnail);
            this.tvName = view.findViewById(R.id.tvName);
            this.tvRate = view.findViewById(R.id.tvRate);
        }
    }
}
