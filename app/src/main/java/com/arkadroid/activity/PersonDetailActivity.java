package com.arkadroid.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arkadroid.BaseApplication;
import com.arkadroid.R;
import com.arkadroid.dagger.modules.GlideApp;
import com.arkadroid.models.PersonDetailModel;
import com.arkadroid.presenters.PersonDetailPresenter;
import com.arkadroid.utils.SpannableStringBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import static java.io.File.separator;

/**
 * @author Dwi Setiyono <dwi.setiyono@ovo.id>
 * @since 2018.04.01
 */
public class PersonDetailActivity extends BaseActivity implements PersonDetailPresenter.PersonDetailView {

    public static final String EXTRA_PERSON_ID = "extra_person_id";
    public static final String EXTRA_PERSON_NAME = "extra_person_name";

    @Inject
    PersonDetailPresenter presenter;

    private ImageView ivThumbnail;
    private View loadingProgressView;
    private View detailsView;
    private TextView tvBirthday;
    private TextView tvPlaceOfBirth;
    private TextView tvBiography;
    private TextView tvWebSite;
    private TextView tvAlsoKnownAs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportPostponeEnterTransition();

        BaseApplication.getInstance().getApplicationComponent().inject(this);

        setContentView(R.layout.activity_person_detail);

        initViews();
        setNavigationAndToolBar();

        int movieId = getIntent().getIntExtra(EXTRA_PERSON_ID, 0);
        presenter.startNow(this, movieId);
    }

    private void initViews() {
        ivThumbnail = findViewById(R.id.ivThumbnail);
        loadingProgressView = findViewById(R.id.loadingProgressView);
        detailsView = findViewById(R.id.detailsView);
        tvBirthday = findViewById(R.id.tvBirthDay);
        tvPlaceOfBirth = findViewById(R.id.tvPlaceOfBirth);
        tvBiography = findViewById(R.id.tvBiography);
        tvAlsoKnownAs = findViewById(R.id.tvAlsoKnownAs);
        tvWebSite = findViewById(R.id.tvWebSite);
    }

    private void setNavigationAndToolBar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitleEnabled(true);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = collapsingToolbar.getLayoutParams();
        layoutParams.height = (int) (1.5 * width);
        collapsingToolbar.invalidate();
        collapsingToolbar.requestLayout();

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            String movieName = getIntent().getStringExtra(EXTRA_PERSON_NAME);
            collapsingToolbar.setTitle(movieName);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.back_arrow);
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);
            supportActionBar.setHomeAsUpIndicator(drawable);
        }
    }

    @Override
    public void showPersonDetail(PersonDetailModel model) {
        if (isFinishing() || model == null) {
            exit();
            Toast.makeText(this, R.string.no_movie_detail, Toast.LENGTH_LONG).show();
            return;
        }

        hideLoadingProgress();

        detailsView.setVisibility(View.VISIBLE);

        String thumbnail = model.getProfile_path();

        GlideApp.with(this)
                .load(thumbnail)
                .centerCrop()
                .error(R.drawable.ic_person_black_24dp)
                .placeholder(R.drawable.ic_person_black_24dp)
                .dontAnimate()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .into(ivThumbnail);

        tvBirthday.setText(String.valueOf(model.getBirthday()));
        tvPlaceOfBirth.setText(String.valueOf(model.getPlace_of_birth()));

        String textDo = " do ";
        String textIs = " is ";
        String textAre = " are ";

        SpannableString biography = SpannableStringBuilder
                .init(model.getBiography())
                .makeBold(textDo)
                .makeBold(textIs)
                .makeBold(textAre)
                .setColor(textDo, getResources().getColor(R.color.black))
                .setColor(textIs, getResources().getColor(R.color.black))
                .setColor(textAre, getResources().getColor(R.color.black))
                .create();

        tvBiography.setText(biography);

        List<String> countries = model.getAlso_known_as();
        StringBuilder builder;
        if (countries != null) {
            builder = new StringBuilder();
            int size = countries.size();
            for (int i = 0; i < size; i++) {
                String name = countries.get(i);
                if (name != null && name.trim().length() > 0) {
                    builder.append(name);
                    if (i < size - 1) {
                        builder.append(separator);
                    }
                }
            }
            if (builder.length() > 0) {
                tvAlsoKnownAs.setText(builder.toString());
            }
        }

        String homepage = model.getHomepage();
        if (homepage != null && homepage.trim().length() > 0) {
            tvWebSite.setText(homepage);
        }
    }

    @Override
    public void showLoadingProgress() {
        if (isFinishing()) {
            return;
        }
        loadingProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingProgress() {
        if (isFinishing()) {
            return;
        }
        loadingProgressView.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.cleanUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exit() {
        ActivityCompat.finishAfterTransition(this);
        presenter.cleanUp();
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        hideLoadingProgress();
        if (throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException) {
            Snackbar.make(detailsView, getString(R.string.check_network_connection) + " : " + throwable.getMessage(), Snackbar.LENGTH_LONG).show();
            return;
        }
        Snackbar.make(detailsView, getString(R.string.something_went_wrong) + " : " + throwable.getMessage(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
