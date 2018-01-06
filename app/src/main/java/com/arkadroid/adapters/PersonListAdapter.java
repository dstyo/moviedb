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
import com.arkadroid.models.PersonModel;
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
public class PersonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ListPreloader.PreloadSizeProvider<PersonModel>,
        ListPreloader.PreloadModelProvider<PersonModel> {

    private final LayoutInflater inflater;
    private List<PersonModel> persons = new ArrayList<>();
    private Activity activity;

    private OnPersonSelectionListener personSelectionListener;

    private GlideRequest<Drawable> requestBuilder;

    private int[] actualDimensions;

    public PersonListAdapter(Activity activity) {

        this.activity = activity;
        inflater = LayoutInflater.from(activity);

        GlideRequests requestManager = GlideApp.with(activity);
        requestBuilder = requestManager.asDrawable().fitCenter();

        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.row_person, parent, false);

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

        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final PersonViewHolder holder = (PersonViewHolder) viewHolder;
        final PersonModel model = persons.get(position);

        String name = model.getName();
        holder.tvName.setText(name);

        String thumbnail = model.getProfile_path();
        requestBuilder.clone()
                .load(thumbnail)
                .placeholder(R.drawable.ic_person_black_24dp)
                .error(R.drawable.ic_person_black_24dp)
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivThumbnail);

        GlideApp.with(activity).load(model.getProfile_path()).centerInside().preload();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (personSelectionListener != null) {
                    personSelectionListener.onPersonSelected(model, holder.ivThumbnail, position);
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
        return persons.size();
    }

    public OnPersonSelectionListener getPersonSelectionListener() {
        return personSelectionListener;
    }

    public void setPersonSelectionListener(OnPersonSelectionListener personSelectionListener) {
        this.personSelectionListener = personSelectionListener;
    }

    public List<PersonModel> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonModel> persons) {
        if (persons == null) {
            return;
        }
        this.persons = persons;
        notifyDataSetChanged();
    }

    public void notifyMoviesListChanged() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public List<PersonModel> getPreloadItems(int position) {
        return Collections.singletonList(persons.get(position));
    }

    @Nullable
    @Override
    public RequestBuilder getPreloadRequestBuilder(PersonModel item) {

        return requestBuilder
                .clone()
                .load(item.getProfile_path());
    }

    @Nullable
    @Override
    public int[] getPreloadSize(PersonModel item, int adapterPosition, int perItemPosition) {
        return actualDimensions;
    }

    public interface OnPersonSelectionListener {

        void onPersonSelected(PersonModel model, View view, int position);
    }

    private static class PersonViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView tvName;
        public ImageView ivThumbnail;

        public PersonViewHolder(View view) {
            super(view);
            this.view = view;
            this.ivThumbnail = view.findViewById(R.id.ivThumbnailPerson);
            this.tvName = view.findViewById(R.id.tvName);
        }
    }
}
