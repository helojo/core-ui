package com.joy.ui.interfaces;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.joy.ui.permissions.Permissions;
import com.trello.rxlifecycle.LifecycleProvider;

import rx.Observable;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Daisw on 17/11/13.
 */
public interface BaseView<E> extends LifecycleProvider<E> {

    boolean isFinishing();
    void finish();

    void showView(View v);
    void hideView(View v);
    void goneView(View v);

    void showImageView(ImageView iv, @DrawableRes int resId);
    void showImageView(ImageView iv, Drawable drawable);
    void hideImageView(ImageView iv);
    void goneImageView(ImageView iv);

    void showToast(String text);
    void showToast(@StringRes int resId);
    void showToast(@StringRes int resId, Object... formatArgs);

    void showSnackbar(@NonNull CharSequence text);
    void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration);
    void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int textColor);
    void showSnackbar(@NonNull CharSequence text, @Snackbar.Duration int duration, @ColorInt int bgColor, @ColorInt int textColor);

    <T extends View> T inflateLayout(@LayoutRes int layoutResId);
    <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root);
    <T extends View> T inflateLayout(@LayoutRes int layoutResId, @Nullable ViewGroup root, boolean attachToRoot);

    ContentResolver getContentResolver();

    @TargetApi(M)
    int checkSelfPermission(@NonNull String permission);
    @TargetApi(M)
    Observable<Permissions> requestPermissions(@NonNull String... permissions);
}
